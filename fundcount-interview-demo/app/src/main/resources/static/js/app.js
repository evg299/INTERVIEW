var PAGE_VARS = {
    rootContext: window.location.pathname
};

var PAGE_FUNCTIONS = {
    getFormData: function () {
        return {
            amountUSD: $("input#amount").val(),
            timestampBuy: PAGE_FUNCTIONS.getTimestampUTC(),
            timestampSell: PAGE_FUNCTIONS.getNowTimestampUTC()
        };
    },

    getNowTimestampUTC: function () {
        var now = new Date();
        return now.getTime() - now.getTimezoneOffset() * 60 * 1000;
    },

    getTimestampUTC: function () {
        var now = $("input#date").datepicker('getDate');
        if (now) {
            return now.getTime() - now.getTimezoneOffset() * 60 * 1000;
        }
        return 0;
    }
};

$(document).ready(function () {
    $("input#date").datepicker({
        language: navigator.language,
        autoclose: true
    });

    $("input#date").datepicker("setDate", new Date());


    $("#recalculate-form").submit(function (event) {
        event.preventDefault();
        $("button#recalculate").prop('disabled', true);

        var data = PAGE_FUNCTIONS.getFormData();
        // console.log(data);

        $.ajax({
            url: PAGE_VARS.rootContext + "api/recalculate",
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            error: function (e) {
                $("button#recalculate").prop('disabled', false);
                $("input#result").val("");

                $('#error-msg').text("Calculation error: " + e['responseJSON']['errorMsg']);
                $('#error-msg').show();
            },
            success: function (data) {
                // console.log("success", data);
                $("button#recalculate").prop('disabled', false);
                $("input#result").val(data['profitRUB']);
                $('#error-msg').hide();
            }
        });

    });
});