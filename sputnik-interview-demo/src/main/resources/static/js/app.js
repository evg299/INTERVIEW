var PAGE_VARS = {
    rootContext: window.location.pathname
};

var PAGE_FUNCTIONS = {
    setSelectOptions: function () {
        $.ajax({
            url: PAGE_VARS.rootContext + "directories/colors"
        }).then(function (data) {
            $("#product-color").empty();
            for (var idx in data) {
                var value = data[idx];
                $("#product-color").append($('<option />', {value: value})
                    .append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, value)));
            }
        });

        $.ajax({
            url: PAGE_VARS.rootContext + "directories/kindsOfClothes"
        }).then(function (data) {
            $("#product-kindOfClothes").empty();
            for (var idx in data) {
                var value = data[idx];
                $("#product-kindOfClothes").append($('<option />', {value: value})
                    .append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, value)));
            }
        });

        $.ajax({
            url: PAGE_VARS.rootContext + "directories/places"
        }).then(function (data) {
            $("#product-place").empty();
            for (var idx in data) {
                var value = data[idx];
                $("#product-place").append($('<option />', {value: value})
                    .append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, value)));
            }
        });
    },

    fillModalDialog: function (product) {
        $("#product-id").val(product['id']);
        $("#product-place").val(product['place']);
        $("#product-kindOfClothes").val(product['kindOfClothes']);
        $("#product-size").val(product['size']);
        $("#product-cost").val(product['cost']);
        $("#product-color").val(product['color']);
        $("#product-description").val(product['description']);
    },

    productFromModalDialog: function () {
        return {
            id: $("#product-id").val(),
            place: $("#product-place").val(),
            kindOfClothes: $("#product-kindOfClothes").val(),
            size: $("#product-size").val(),
            cost: $("#product-cost").val(),
            color: $("#product-color").val(),
            description: $("#product-description").val()
        };
    },

    fillTableRow: function (row, product) {
        row.append($('<th/>', {scope: 'row'}).append(product['id']));
        row.append($('<td/>').append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, product['place'])));
        row.append($('<td/>').append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, product['kindOfClothes'])));
        row.append($('<td/>').append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, product['color'])));
        row.append($('<td/>').append(product['size']));
        row.append($('<td/>').append(product['cost']));
        row.append($('<td/>').append(product['description']));

        var actions = $('<td/>');

        actions.append($('<button/>', {
            id: 'start-edit-' + product['id'],
            type: 'button',
            class: 'btn btn-primary btn-sm'
        }).append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, "Edit")).on("click", function () {
            PAGE_FUNCTIONS.fillModalDialog(product);
            $("#detailDialogModal").modal('show');
        }));

        actions.append($('<button/>', {
            id: 'start-delete-' + product['id'],
            type: 'button',
            class: 'btn btn-secondary btn-sm'
        }).append(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, "Delete")).on("click", function () {
            $("#delete-product-id").val(product['id']);
            $("#confirmDeleteModal").modal('show');
        }));

        row.append(actions);
    }
};

// main logic
$(document).ready(function () {
    // directories
    PAGE_FUNCTIONS.setSelectOptions();


    // init add
    $("#start-add-product").on("click", function () {
        PAGE_FUNCTIONS.fillModalDialog({});
        $("#detailDialogModal").modal('show');
    });

    // save
    $("#product-form").submit(function (event) {
        event.preventDefault();

        var data = PAGE_FUNCTIONS.productFromModalDialog();

        $.ajax({
            url: PAGE_VARS.rootContext + "products",
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            error: function (e) {
                console.log("error", e);
                alert(e.responseJSON.error)
            },
            success: function (data) {
                console.log("success", data);
                refreshTable();
                $("#detailDialogModal").modal('hide');
            }
        });
    });

    // delete
    $("#delete-product").on("click", function () {
        $.ajax({
            url: PAGE_VARS.rootContext + "products/" + $("#delete-product-id").val(),
            type: "DELETE",
            error: function (e) {
                console.log("error", e);
            },
            success: function (data) {
                refreshTable();
                $("#confirmDeleteModal").modal('hide');
            }
        });
    });

    // table
    var refreshTable = function () {
        $.ajax({
            url: PAGE_VARS.rootContext + "products"
        }).then(function (data) {
            $("#product-table").find('tbody').empty();
            for (var idx in data) {
                var product = data[idx];
                var row = $('<tr/>', {id: 'product-' + product['id']});
                PAGE_FUNCTIONS.fillTableRow(row, product);
                $("#product-table").find('tbody').append(row);
            }
        });
    };

    $("#refresh-products-table").on("click", function (e) {
        refreshTable();
    });

    refreshTable();
});