// Пример - Получить данные текущей строки.
// Технополис 2017
// Вы можете получить данные текущей строки текущего объекта.
var Jewel = Java.type("Jewel.Master");
var W1 = new Jewel(data);
W1.putData("Наименование","Это строка в колонке Наименование");

var prop = '{"title":"testInput", "screen":"10,10,50,30"}';
W1.winOpen(prop);
var json = W1.getDataRow();
json = JSON.parse(json);
W1.say(json.Код+"-"+json.Наименование);
