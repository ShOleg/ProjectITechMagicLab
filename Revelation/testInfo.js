// Пример контроля по словарям в форме ввода.
// Технополис 2018

var Jewel = Java.type("Jewel.Master");
var W1 = new Jewel(act);
var style = "-fx-font-size:40px";

var prop = '{"title":"Пример контроля ввода сотрудника", "screen":"15,15,30,8"}';
W1.winOpen(prop);

var v1 = '{           "type":"label", "value":"Сотрудник ", "grid":"0,0"}';
var v2 = '{ "id":"v2","type":"text" , "value":"Фамилия",  "action":"funcTableFromButton"  , "grid":"1,0"}';
var v3=  '{           "type":"button","value":"..."  ,  "action":"funcTableFromButton"  , "grid":"2,0"}';

W1.form( v1, v2, v3);

/*
function funcTableFromText() {
	var W100 = new Jewel();
        var json = W100.getInfo("Словарь.КлассАктив","Код","Тара");
        json = JSON.parse(json);
	return json.Код;
}
*/

function funcTableFromButton() {
	var W100 = new Jewel();
        var json = W100.getInfo("Картотека.Сотрудник","Сотрудник","Кого искать");
        json = JSON.parse(json);
	var xxx = W1.setText("v2", json.Сотрудник);
	return json.Сотрудник;
}
