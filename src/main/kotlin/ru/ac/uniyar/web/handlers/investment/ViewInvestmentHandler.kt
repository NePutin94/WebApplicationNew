package ru.ac.uniyar.web.handlers.investment

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiBodyLens
import org.http4k.lens.Query
import org.http4k.lens.int
import org.http4k.routing.path
import org.http4k.template.ViewModel
import ru.ac.uniyar.domain.database.filters.makeInvestmentFilterExpr
import ru.ac.uniyar.domain.operations.queries.InvestmentFetchOperation
import ru.ac.uniyar.domain.operations.queries.InvestmentListOperation
import ru.ac.uniyar.web.filters.*
import ru.ac.uniyar.web.models.investment.InvestmentDetailedVM
import ru.ac.uniyar.web.models.investment.InvestmentViewVM

fun detailedInvestmentViewHandler(
    investmentFetchOperation: InvestmentFetchOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->
        val investId = request.path("id")!!.toInt()
        val investment = investmentFetchOperation.fetch(investId)!!
        val viewModel = InvestmentDetailedVM(investment)
        Response(Status.OK).with(htmlView of viewModel)
    }

fun listInvestmentViewHandler(
    investmentListOperation: InvestmentListOperation,
    htmlView: BiDiBodyLens<ViewModel>
): HttpHandler =
    { request ->

        val startDateL = lensOrNull(InvestmentSearchFilter.startDateLField, request)?.atStartOfDay()
        val startDateR = lensOrNull(InvestmentSearchFilter.startDateRField, request)?.atStartOfDay()
        val filterExpr = makeInvestmentFilterExpr(startDateL, startDateR)

        val pageTotal = investmentListOperation.getPagesCount { filterExpr }
        val index = Query.int().defaulted("page", 1)
        val page = index(request)

        val pagination = investmentListOperation.listPaginationFiltered(page - 1) { filterExpr }

        val viewModel =
            InvestmentViewVM(
                pagination,
                page,
                pageTotal,
                InvestmentFilterParams(startDateL?.toLocalDate(), startDateR?.toLocalDate()),
                request.uri.query.replace("&page=\\d+".toRegex(), ""),
                request.uri.toString().replace("?", "*").replace("&", "~")
            )
        Response(Status.OK).with(htmlView of viewModel)
    }