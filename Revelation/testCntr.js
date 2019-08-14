// Пример эффектов оформления и контроля ввода. Технополис 2017
var Jewel = Java.type("Jewel.Master");
var W0 = new Jewel();
var W1 = new Jewel(act);

var prop = '{"title":"Тест оформления элементов формы", "screen":"13,10,64,54"}';
W1.winOpen(prop);

var v1 = '{           "type":"label" , "value":"Тип text" ,  "grid":"0,0", "size":205, "alignment":"right", "margin":{"top":15,"right":5,"bottom":15,"left":5}, "effect":"boxBlur", "style":"-fx-font-size:44px; color: #f15a22; -fx-background-color: #CCFF99; -fx-text-fill: black" }';
var v2 = '{ "id":"v2","type":"text"  , "value":"Ваша галактика",  "grid":"1,0", "size":200, "action":"func100", "placeholder":"Норильск", "margin":{"top":5,"right":5,"bottom":15,"left":5}, "tooltip":"Это текст подписи", "effect":"reflection", "style":"-fx-font-size:14px; color: #f15a22; -fx-background-color: #CCFF99; -fx-text-fill: black" }';
var v3 = '{           "type":"button", "value":"Кнопка", "grid":"2,0", "size":70,  "action":"func101", "margin":{"top":5,"right":5,"bottom":5,"left":5}, "effect":"dropShadow"}';

var v4 = '{           "type":"label",  "value":"Тип date",   "grid":"0,1", "size":205, "alignment":"right", "margin":{"top":5,"right":5,"bottom":5,"left":5}, "effect":"boxBlur", "style":"-fx-font-size:25px; color: #f15a22; -fx-background-color: #CCFF99; -fx-text-fill: black" }';
var v5 = '{ "id":"v8","type":"date" ,  "value":"",       "grid":"1,1", "size":200, "action":"func102", "margin":{"top":5,"right":5,"bottom":5,"left":5}, "padding":{"top":15,"right":5,"bottom":15,"left":5} }';

var v6 = '{           "type":"label",    "value":"Флаг типа сheckBox",  "grid":"0,2", "size":205, "alignment":"right", "margin":{"top":5,"right":15,"bottom":5,"left":5}, "effect":"glow", "style":"-fx-font-size:18px; color: #f15a22; -fx-text-fill: red" }';
var v7 = '{ "id":"v8","type":"сheckBox", "value":"",          "grid":"1,2", "size":200, "action":"func103", "margin":{"top":5,"right":5,"bottom":5,"left":5} }';

var v8 = '{           "type":"label" , "value":"Тип number", "grid":"0,3", "size":205, "alignment":"right", "margin":{"top":5,"right":5,"bottom":5,"left":5}, "effect":"innerShadow", "style":"-fx-font-size:24px; color: #f15a22; -fx-background-color: #CCFF99; -fx-text-fill: black" }';
var v9 = '{ "id":"v8","type":"number", "value":0,        "grid":"1,3", "size":200, "action":"func104", "format":"99999.999", "margin":{"top":5,"right":5,"bottom":5,"left":5} }';

var v10 ='{           "type":"label"   , "value":"Город", "grid":"0,4", "size":205,  "alignment":"right", "margin":{"top":5,"right":5,"bottom":5,"left":5}, "effect":"lighting", "style":"-fx-font-size:24px; color: #f15a22; -fx-background-color: #CCFF99; -fx-text-fill: black" }';
var v11= '{"id":"v10","type":"listview", "value":"Одесса,Красноярск,Москва,Ленинград","grid":"1,4", "size":200, "action":"func105", "margin":{"top":5,"right":5,"bottom":5,"left":5}, "effect":"dropShadow"} ';

var v12 ='{           "type":"label",    "value":"Блюдо", "grid":"0,5", "size":205,  "alignment":"right" , "margin":{"top":5,"right":5,"bottom":5,"left":5}}';
var v13= '{"id":"v13","type":"radio",    "value":"Борщ, Второе, Компот, Салат","grid":"1,5", "size":100, "action":"func106", "margin":{"top":5,"right":5,"bottom":5,"left":5}, "style":"-fx-font-size:14px; -fx-text-fill: black"} ';

var v14 ='{           "type":"label"   , "value":"Выбор combobox", "grid":"0,6", "size":205,"alignment":"right", "margin":{"top":5,"right":5,"bottom":5,"left":5} }';
var v15= '{"id":"v15","type":"combobox", "value":"Один,Два,Три,Четыре", "grid":"1,6", "size":200, "action":"func107", "margin":{"top":5,"right":5,"bottom":15,"left":5}, "effect":"bloom", "style":"-fx-font-size:24px; color: #f15a22; -fx-background-color: #ffffff ; -fx-text-fill: black"} ';

W1.form( v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15);

function func100(text) {
	var x = W1.getInfo(text);
	return x;
}

function func101() {
	W1.alert("вызов func101");
}

function func102(text) {
	W1.alert(text);
	return text;
}

function func103(text) {
	W1.alert(text);
	return text;
}

function func104(text) {
	W1.alert(text);
	return text;
}

function func105(text) {
	W1.alert(text);
	return text;
}

function func106() {
	W1.alert(W1.getRadioButton("v13"));
}

function func107(text) {
	W1.alert(W1.getComboBox("v15"));
	return text;
}
