// Пример обработки переменных
// Технополис 2017

var Jewel = Java.type("Jewel.Master");
var Jewel = new Jewel(data);

var recParent = Jewel.getParent();
var recSostav = Jewel.getSostav("Документ_ТМЦ_Приход");

var summaSostav = 0;

 for (rec in recSostav) {
	summaSostav = summaSostav + recSostav[rec].СуммаВсего;
 }

if(recParent.Сумма !=  summaSostav){
	Jewel.say("плохо","1",15);
}else{
	Jewel.say("хорошо","1",15);
}
