const mealAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: getFilteredAndUpdateTable
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {"data": "dateTime"},
                {"data": "description"},
                {"data": "calories"},
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [0, "desc"]
            ]
        })
    )
})

function getFilteredAndUpdateTable() {
    $.ajax({
        url: ctx.ajaxUrl + "filter",
        type: "GET",
        data: $("#filterForm").serialize()
    }).done(updateTableByData)
}

function resetFilter() {
    $("#filterForm")[0].reset();
    getAllAndUpdateTable();
}
