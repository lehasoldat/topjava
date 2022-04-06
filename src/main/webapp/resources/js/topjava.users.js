const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

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

function changeStatus(cb) {
    let id = $(cb).closest("tr").attr("id");
    $.ajax({
        url: ctx.ajaxUrl + id + "/enable",
        type: "POST",
        data: {
            "userStatus": cb.checked
        }
    }).done(function () {
        updateTable();
        if (cb.checked) {
            successNoty("User enabled");
        } else {
            successNoty("User disabled")
        }
    })
}

function updateView() {
    updateTable();
}