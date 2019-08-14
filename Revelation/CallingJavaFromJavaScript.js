/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Java */

var BigDecimal = Java.type('java.math.BigDecimal');

function calculate(amount, percentage) {
    var result = new BigDecimal(amount).multiply(new BigDecimal(percentage)).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_EVEN);
    return result;
}



