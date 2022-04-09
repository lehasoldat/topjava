const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: getAllAndUpdateTable
}


// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
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
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function changeStatus(cb, id) {
    let enable = $(cb).is(":checked");
    $.ajax({
        url: ctx.ajaxUrl + id + "/enable",
        type: "POST",
        data: {
            "userStatus": enable
        }
    }).done(function () {
        $(cb).closest("tr").attr("data-userEnabled", enable);
        successNoty(enable ? "User enabled" : "User disabled")
    }).fail(function () {
        $(cb).param("checked", !enable);
    })
}
