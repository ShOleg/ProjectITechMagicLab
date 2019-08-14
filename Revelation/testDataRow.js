// Пример - Получить данные текущей строки.
// Технополис 2017
// Вы можете получить данные текущей строки текущего объекта.
var Jewel = Java.type("Jewel.Master");
var Xd = new Jewel(data);

var prop = '{"title":"testDataRow", "screen":"10,10,50,30"}';
Xd.winOpen(prop);
var json = Xd.getDataRow();
json = JSON.parse(json);
Xd.say(json.Код+"-"+json.Наименование);
