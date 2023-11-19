package com.example.kalkulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    var liczba1: String = ""
    var liczba2: String = ""
    var wynik: String = ""
    var dzialanie: String = ""
    var sprawdzeniePrzycisku: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onClickDigit(view: View){
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        if(sprawdzeniePrzycisku){
            wpisywanie.text=""
            sprawdzeniePrzycisku = false
        }
        var tekst:String = "" + wpisywanie.text
        if(tekst=="0"){
            tekst=""
        }
        wpisywanie.text = tekst + (view as Button).text
    }
    fun onClickOperator(view: View) {
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        liczba1 = wpisywanie.text.toString()
        when (view.id) {
            R.id.plus -> dzialanie = "+"
            R.id.minus -> dzialanie = "-"
            R.id.times -> dzialanie = "*"
            R.id.divide -> dzialanie = "/"
            R.id.fact -> dzialanie = "!"
            R.id.power -> dzialanie = "^"
            R.id.over -> dzialanie = "()"
            R.id.mod -> dzialanie = "mod"
        }
        val calosc = findViewById<TextView>(R.id.calosc)
        if(liczba1 != ""){
            calosc.text = liczba1 + dzialanie
        }
        sprawdzeniePrzycisku = true
        if(dzialanie == "!" && liczba1 != ""){
            if(liczba1.contains("-")){
                wynik="Error"
                wpisywanie.text=wynik
                liczba1 = ""
                liczba2 = ""
                dzialanie = ""
                sprawdzeniePrzycisku = true
                wynik = ""
            }
            else if(isValidInt(liczba1)==false){
                wynik="Error"
                wpisywanie.text=wynik
                liczba1 = ""
                liczba2 = ""
                dzialanie = ""
                sprawdzeniePrzycisku = true
                wynik = ""
            }
            else {
                wynik = silnia(liczba1)
                wpisywanie.text = wynik
                liczba1 = ""
                liczba2 = ""
                dzialanie = ""
                sprawdzeniePrzycisku = true
                wynik = ""
            }
        }
    }
    fun onClickEqual(view: View) {
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        val calosc = findViewById<TextView>(R.id.calosc)
        liczba2 = wpisywanie.text.toString()
        if(dzialanie.equals("")){
            wynik = liczba2
            if(liczba2.contains("-")){
                calosc.text = "("+liczba2+")"
            }
            else{
                calosc.text = liczba2 + "="
            }
            wpisywanie.text=wynik
            liczba1 = ""
            liczba2 = ""
            dzialanie = ""
            sprawdzeniePrzycisku = true
            wynik = ""
        }
        else if(liczba2.equals("")){
            wynik = liczba1
            if(liczba2.contains("-")){
                calosc.text = "("+liczba1+")"
            }
            else{
                calosc.text = liczba1 + "="
            }
            wpisywanie.text=wynik
            liczba1 = ""
            liczba2 = ""
            dzialanie = ""
            sprawdzeniePrzycisku = true
            wynik = ""
        }
        else if(liczba1.equals("")){
            wynik = liczba2
            if(liczba2.contains("-")){
                calosc.text = "("+liczba2+")"
            }
            else{
                calosc.text = liczba2 + "="
            }
            wpisywanie.text=wynik
            liczba1 = ""
            liczba2 = ""
            dzialanie = ""
            sprawdzeniePrzycisku = true
            wynik = ""
        }
        else{
            if(liczba2.toBigDecimal().intValueExact()==0 && dzialanie == "/"){
                calosc.text = liczba1 +dzialanie+ liczba2 + "="
                wynik = "Error"
                wpisywanie.text = wynik
                liczba1 = ""
                liczba2 = ""
                dzialanie = ""
                sprawdzeniePrzycisku = true
                wynik = ""
            }
            else{
                when (dzialanie) {
                    "+" -> wynik = add(liczba1,liczba2)
                    "-" -> wynik = sub(liczba1,liczba2)
                    "*" -> wynik = mul(liczba1,liczba2)
                    "/" -> wynik = div(liczba1,liczba2)
                    "^" -> wynik = potega(liczba1,liczba2)
                    "()" -> wynik = newton(liczba1,liczba2)
                    "mod" -> wynik = mod(liczba1,liczba2)
                }
                if(liczba1.contains("-") && liczba2.contains("-")){
                    calosc.text = "("+liczba1+")"+dzialanie+ "("+liczba2+")"+ "="
                }
                else if(liczba1.contains("-")){
                    calosc.text = "("+liczba1+")"+dzialanie+liczba2+ "="
                }
                else if(liczba2.contains("-")){
                    calosc.text = liczba1+dzialanie+ "("+liczba2+")"+ "="
                }
                else{
                    calosc.text = liczba1 +dzialanie+ liczba2 + "="
                }
                wpisywanie.text = wynik
                liczba1 = ""
                liczba2 = ""
                dzialanie = ""
                sprawdzeniePrzycisku = true
                wynik = ""
            }
        }
    }
    fun onClickC(view: View) {
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        val calosc = findViewById<TextView>(R.id.calosc)
        calosc.text = ""
        wpisywanie.text=""
        liczba1 = ""
        liczba2 = ""
        dzialanie = ""
        sprawdzeniePrzycisku = true
    }
    fun onClickCE(view: View) {
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        wpisywanie.text=""

    }
    fun onClickBack(view: View) {
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        wpisywanie.text = wpisywanie.text.dropLast(1)
    }
    fun onClickPlusMinus(view: View) {
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        if(wpisywanie.text.startsWith("-")){
            wpisywanie.text = wpisywanie.text.drop(1)
        }
        else {
            wpisywanie.text = "-"+wpisywanie.text
        }
    }
    fun onClickDot(view: View) {
        val wpisywanie = findViewById<TextView>(R.id.rezultat)
        if(wpisywanie.text.contains(".")){

        }
        else{
            if(wpisywanie.text==""){
                wpisywanie.text = "0."
            }
            else{
                wpisywanie.text = ""+wpisywanie.text+"."
            }
        }
    }
    fun add(l1: String, l2: String):String {
        return (l1.toBigDecimal()+l2.toBigDecimal()).toPlainString()
    }
    fun sub(l1: String, l2: String):String {
        return (l1.toBigDecimal()-l2.toBigDecimal()).toPlainString()
    }
    fun mul(l1: String, l2: String):String {
        return (l1.toBigDecimal()*l2.toBigDecimal()).toPlainString()
    }
    fun div(l1: String, l2: String):String {
        return l1.toBigDecimal().divide(l2.toBigDecimal(),10, RoundingMode.HALF_UP).toPlainString()
    }
    fun silnia(l1: String):String {
        var n = 1
        for (i in 2..l1.toBigDecimal().intValueExact()){
            n=n*i
        }
        return n.toString()
    }
    fun potega(l1: String, l2: String):String {
        if(l2.contains(".")){
            return "Error"
        }
        else{
            if(l2.toBigDecimal().toInt()==0){
                return 1.toString()
            }
            if(l2.toBigDecimal().toInt()>=1){
                if(l1.toBigDecimal().toDouble()==0.0){
                    return 0.toString()
                }
                var x=1.0
                for (i in 1..l2.toBigDecimal().toInt()){
                    x=x*l1.toBigDecimal().toDouble()
                }
                return x.toString()
            }
            else if(l2.toBigDecimal().toInt()<=-1){
                var l3=div(1.toString(),l1)
                var l4=-l2.toBigDecimal().toDouble()
                var x=1.0
                for (i in 1..l4.toBigDecimal().toInt()){
                    x=x*l3.toBigDecimal().toDouble()
                }
                return x.toString()

            }
            return "Error"
        }
    }
    fun isValidInt(l1: String): Boolean {
        return l1.toIntOrNull() != null
    }
    fun newton(l1: String, l2: String):String {
        if (l1.toBigDecimal().toInt()<l2.toBigDecimal().toInt()){
            return "Error"
        }
        else if(isValidInt(l1)==false || isValidInt(l2)==false){
            return "Error"
        }
        else if(l1.toBigDecimal().toInt()<0 || l2.toBigDecimal().toInt()<0){
            return "Error"
        }
        else if(l1.toBigDecimal().toInt()==l2.toBigDecimal().toInt()){
            return 1.toString()
        }
        else {
            var k = 1
            var y = ""
            if(l1.toBigDecimal().toInt()-l2.toBigDecimal().toInt()>l2.toBigDecimal().toInt()){
                k = l1.toBigDecimal().toInt()-l2.toBigDecimal().toInt()
                y = l2
            }
            else{
                k = l2.toBigDecimal().toInt()
                y = sub(l1,l2)
            }
            var n = l1.toBigDecimal().toInt()
            var x = 1
            for (i in k+1..n){
                x=x*i
            }
            return x.div(silnia(y).toInt()).toString()
        }
    }
    fun mod(l1: String, l2: String):String {
        if(l1.toBigDecimal().toDouble()<0){
            return (l2.toBigDecimal().toDouble() - (-l1.toBigDecimal().toDouble()%l2.toBigDecimal().toDouble())).toString()
        }
        else{
            return (l1.toBigDecimal().toDouble()%l2.toBigDecimal().toDouble()).toString()
        }
    }

}