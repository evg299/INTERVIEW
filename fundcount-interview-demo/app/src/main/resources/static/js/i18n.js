var DICTIONARIES = {
    userLang: navigator.language,

    "ru-RU": {
        "Profit calculation": "Расчет прибыли",
        "Buy date": "Дата покупки",
        "Amount USD": "Количество USD",
        "Recalculate": "Перерасчет",
        "Profit RUB": "Прибыль в рублях"
    }
};

var I18N_FUNCTIONS = {
    resolveString: function (lang, str) {
        if (!DICTIONARIES[lang] || !DICTIONARIES[lang][str])
            return str;

        return DICTIONARIES[lang][str];
    }
};

$(document).ready(function () {
    $(".i18n_marker").each(function () {
        $(this).text(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, $(this).text().trim()));
    });
});