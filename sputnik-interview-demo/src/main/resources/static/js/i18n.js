var DICTIONARIES = {
    userLang: navigator.language || navigator.userLanguage,

    "ru-RU": {
        "Products store": "Магазин",

        "Close": "Закрыть",
        "Delete": "Удалить",
        "Are you sure?": "Вы уверены?",
        "Product deleting ...": "Удаление товара ...",

        "Add product": "Добавить товар",
        "Refresh": "Обновить",

        "Edit": "Редактировать",
        "Save": "Сохранить",

        "Place": "Местонахождение",
        "Kind of clothes": "Тип одежды",
        "Color": "Цвет",
        "Size": "Размер",
        "Cost": "Стоимость",
        "Description": "Описание",
        "Actions": "Действия",
        "Product detail": "Описание товара",

        "STORE": "Магазин",
        "STORAGE": "Склад",

        "WHITE": "Белый",
        "BLUE": "Синий",
        "RED": "Красный",
        "GREEN": "Зеленый",
        "BLACK": "Черный",

        "DRESS": "Платье",
        "PANTS": "Брюки",
        "SKIRT": "Юбка",
        "VEST": "Жилетка",
        "SHIRT": "Рубашка"
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
    $(".i18n_marker").each(function (index) {
        $(this).text(I18N_FUNCTIONS.resolveString(DICTIONARIES.userLang, $(this).text().trim()));
    });
});