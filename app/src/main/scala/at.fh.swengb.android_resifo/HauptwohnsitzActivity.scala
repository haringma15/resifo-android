package at.fh.swengb.android_resifo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.{ArrayAdapter, EditText, Spinner, TextView}
import scala.util.matching.Regex

/**
  * Created by Martin on 15.01.2017.
  */
class HauptwohnsitzActivity extends Activity{

  var db: Db = _
  var person_id = 0
  val d = new Data

  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.hauptwohnsitz)

    db = Db(getApplicationContext())
    fillAllSpinner()

    person_id = getIntent.getExtras.get("person_id").asInstanceOf[Int]

    val dataMap = d.fillHwsDaten(db, person_id)
    fillDataInTextView(dataMap, person_id)
  }

  def fillDataInTextView(hauptwohnsitzData: Map[Int, Any], person_id: Int) : Unit = {
    val bundesland = hauptwohnsitzData(person_id).asInstanceOf[Hauptwohnsitz].getBundesland()

    findViewById(R.id.eT_hwsStraße).asInstanceOf[TextView].setText(hauptwohnsitzData(person_id).asInstanceOf[Hauptwohnsitz].getStrasse())
    findViewById(R.id.eT_hwsHausNr).asInstanceOf[TextView].setText(hauptwohnsitzData(person_id).asInstanceOf[Hauptwohnsitz].getHausnr())
    findViewById(R.id.eT_hwsStiege).asInstanceOf[TextView].setText(hauptwohnsitzData(person_id).asInstanceOf[Hauptwohnsitz].getStiege())
    findViewById(R.id.eT_hwsTuer).asInstanceOf[TextView].setText(hauptwohnsitzData(person_id).asInstanceOf[Hauptwohnsitz].getTuer())
    findViewById(R.id.eT_hwsPLZ).asInstanceOf[TextView].setText(hauptwohnsitzData(person_id).asInstanceOf[Hauptwohnsitz].getPlz())
    findViewById(R.id.eT_hwsOrt).asInstanceOf[TextView].setText(hauptwohnsitzData(person_id).asInstanceOf[Hauptwohnsitz].getOrt())

    if(bundesland == "Steiermark") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(0)
    } else if(bundesland == "Kärnten") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(1)
    } else if(bundesland == "Burgenland") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(2)
    } else if(bundesland == "Tirol") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(3)
    } else if(bundesland == "Vorarlberg") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(4)
    } else if(bundesland == "Salzburg") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(5)
    } else if(bundesland == "Niederösterreich") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(6)
    } else if(bundesland == "Oberösterreich") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(7)
    } else if(bundesland == "Wien") {
      findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].setSelection(8)
    }
  }

  def saveData(view: View): Unit = {
    val strasse = findViewById(R.id.eT_hwsStraße).asInstanceOf[EditText].getText.toString
    val hausnummer = findViewById(R.id.eT_hwsHausNr).asInstanceOf[EditText].getText.toString
    val stiege = findViewById(R.id.eT_hwsStiege).asInstanceOf[EditText].getText.toString
    val tuer = findViewById(R.id.eT_hwsTuer).asInstanceOf[EditText].getText.toString
    val plz = findViewById(R.id.eT_hwsPLZ).asInstanceOf[EditText].getText.toString
    val ort = findViewById(R.id.eT_hwsOrt).asInstanceOf[EditText].getText.toString
    val bundesland = findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].getSelectedItem().toString()

    val hwsDaten: HauptwohnsitzDaten = HauptwohnsitzDaten(person_id, strasse, hausnummer, stiege, tuer, plz, ort, bundesland)

    val hwsDao = db.mkHwsDao()
    hwsDao.insert(hwsDaten)
  }

  def updateData(view: View) = {
    val strasse = findViewById(R.id.eT_hwsStraße).asInstanceOf[EditText].getText.toString
    val hausnummer = findViewById(R.id.eT_hwsHausNr).asInstanceOf[EditText].getText.toString
    val stiege = findViewById(R.id.eT_hwsStiege).asInstanceOf[EditText].getText.toString
    val tuer = findViewById(R.id.eT_hwsTuer).asInstanceOf[EditText].getText.toString
    val plz = findViewById(R.id.eT_hwsPLZ).asInstanceOf[EditText].getText.toString
    val ort = findViewById(R.id.eT_hwsOrt).asInstanceOf[EditText].getText.toString
    val bundesland = findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner].getSelectedItem().toString()

    val hwsDaten: HauptwohnsitzDaten = HauptwohnsitzDaten(person_id, strasse, hausnummer, stiege, tuer, plz, ort, bundesland)

    val hwsDao = db.mkHwsDao()
    hwsDao.update(hwsDaten, person_id)
  }

  def gotoNext(view:View): Unit ={
    val check: String = getIntent.getExtras.get("update").asInstanceOf[String]
    if (check == "update") {
      updateData(view)
    }
    else {
      saveData(view)
    }
    val i = new Intent(this, classOf[ErfolgreichActivity])
    finish()
    startActivity(i)
  }

  def goBack(view:View): Unit ={
    val check: String = getIntent.getExtras.get("update").asInstanceOf[String]
    if (check == "update"){
      val i = new Intent(this, classOf[OverviewActivity])
      i.putExtra("person_id", person_id)
      finish()
      startActivity(i)
    }
    else {
      val i = new Intent(this, classOf[AnmeldungActivity])
      i.putExtra("person_id", person_id)
      i.putExtra("update", "update")
      finish()
      startActivity(i)
    }
  }

  def fillAllSpinner(): Unit ={
    fillSpinner(findViewById(R.id.s_hwsBundesland).asInstanceOf[Spinner], Array("Steiermark", "Kärnten", "Burgenland", "Tirol", "Vorarlberg", "Salzburg", "Niederösterreich", "Oberösterreich", "Wien"))

    def fillSpinner(spinner: Spinner, content: Array[String]): Unit ={
      val adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, content)
      spinner.setAdapter(adapter)
    }
  }
/*
  def checkText(name: String): String = {
    val check: Regex = ".*\d.*".r
    name match {
      case `check` => name.replace("1","i").replace("2","z").replace("3","e").replace("4","a").replace("5","s").replace("6","g").replace("7","t").replace("8","b").replace("9","p").replace("0","o")
      case _ => name
    }
  }

  def checkNumber(number: String): String = {
    val check: Regex = ".*\s.*".r
    number match {
      case `check` => ""
      case _ => number
    }
  }

  def checkPlz(plz: String) = {
    val check: Regex = "\d\d\d\d".r
    plz match {
      case `check` => plz
      case _ => ""
    }
  }
  */
}
