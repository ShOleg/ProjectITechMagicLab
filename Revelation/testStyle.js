// Пример использования стилей оформления.
// Технополис 2017
var Jewel = Java.type("Jewel.Master");

var W1 = new Jewel();
var prop = '{"title":"testInput", "screen":"15,15,70,50"}';
W1.winOpen(prop);
W1.say("Courier New","-fx-font-size: 16pt; -fx-font-family:'Courier New'; -fx-text-fill: blue;  -fx-background-color: white;");
W1.say("Arial",      "-fx-font-size: 32pt; -fx-font-family:'Arial';       -fx-text-fill: green; -fx-background-color: white;");
W1.say("Serif",      "-fx-font-size: 32pt; -fx-font-family:'Serif';       -fx-text-fill: red;   -fx-background-color: white;");
W1.say("sans-serif", "-fx-font-size: 32pt; -fx-font-family:'sans-serif';  -fx-text-fill: yellow ;    -fx-background-color: black;");
W1.say("cursive",    "-fx-font-size: 32pt; -fx-font-family:'cursive';     -fx-text-fill: magenta ;   -fx-background-color: white;");
W1.say("monospace",  "-fx-font-size: 32pt; -fx-font-family:'monospace';   -fx-text-fill: red;        -fx-background-color: white;");
