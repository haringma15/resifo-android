package at.fh.swengb.android_resifo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget._
import scala.util.matching.Regex

/**
  * Created by Martin on 15.01.2017.
  */
class AnmeldungActivity extends Activity{

  var db: Db = _
  var person_id = 0
  val d = new Data

  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.anmeldung)

    db = Db(getApplicationContext())
    fillAllSpinner()

    person_id = getIntent.getExtras.get("person_id").asInstanceOf[Int]
/*
    val dataMap = d.fillAnmeldeDaten(db, person_id)
    fillDataInTextView(dataMap, person_id)
*/
  }
/*
  def fillDataInTextView(anmeldungData: Map[Int, Any], person_id: Int) : Unit = {
    val bundesland = anmeldungData(person_id).asInstanceOf[Anmeldung].getBundesland()
    val anAusland = anmeldungData(person_id).asInstanceOf[Anmeldung].getZuzugAusAusland()
    val hauptwohnsitzCheckbox = anmeldungData(person_id).asInstanceOf[Anmeldung].getHauptwohnsitz()

    findViewById(R.id.eT_anStraße).asInstanceOf[TextView].setText(anmeldungData(person_id).asInstanceOf[Anmeldung].getStrasse())
    findViewById(R.id.eT_anHausNr).asInstanceOf[TextView].setText(anmeldungData(person_id).asInstanceOf[Anmeldung].getHausnr())
    findViewById(R.id.eT_anStiege).asInstanceOf[TextView].setText(anmeldungData(person_id).asInstanceOf[Anmeldung].getStiege())
    findViewById(R.id.eT_anTuer).asInstanceOf[TextView].setText(anmeldungData(person_id).asInstanceOf[Anmeldung].getTuer())
    findViewById(R.id.eT_anPLZ).asInstanceOf[TextView].setText(anmeldungData(person_id).asInstanceOf[Anmeldung].getPlz())
    findViewById(R.id.eT_anOrt).asInstanceOf[TextView].setText(anmeldungData(person_id).asInstanceOf[Anmeldung].getOrt())

    if(bundesland == "Steiermark") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(0)
    } else if(bundesland == "Kärnten") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(1)
    } else if(bundesland == "Burgenland") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(2)
    } else if(bundesland == "Tirol") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(3)
    } else if(bundesland == "Vorarlberg") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(4)
    } else if(bundesland == "Salzburg") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(5)
    } else if(bundesland == "Niederösterreich") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(6)
    } else if(bundesland == "Oberösterreich") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(7)
    } else if(bundesland == "Wien") {
      findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].setSelection(8)
    }

    if(anAusland == "ja") {
      findViewById(R.id.rB_anAuslandJa).asInstanceOf[RadioButton].setChecked(true)
      findViewById(R.id.rB_anAuslandNein).asInstanceOf[RadioButton].setChecked(false)
    } else {
      findViewById(R.id.rB_anAuslandJa).asInstanceOf[RadioButton].setChecked(false)
      findViewById(R.id.rB_anAuslandNein).asInstanceOf[RadioButton].setChecked(true)
    }

    if(hauptwohnsitzCheckbox == "ja") {
      findViewById(R.id.rB_anHWSJa).asInstanceOf[RadioButton].setChecked(true)
      findViewById(R.id.rB_anHWSNein).asInstanceOf[RadioButton].setChecked(false)
    } else {
      findViewById(R.id.rB_anHWSJa).asInstanceOf[RadioButton].setChecked(false)
      findViewById(R.id.rB_anHWSNein).asInstanceOf[RadioButton].setChecked(true)
    }
    findViewById(R.id.eT_anNameUG).asInstanceOf[TextView].setText(anmeldungData(person_id).asInstanceOf[Anmeldung].getUnterkunftgeber())
  }
*/
  def saveData(view: View): Unit = {
    val strasse = findViewById(R.id.eT_anStraße).asInstanceOf[EditText].getText.toString
    val hausnummer = findViewById(R.id.eT_anHausNr).asInstanceOf[EditText].getText.toString
    val stiege = findViewById(R.id.eT_anStiege).asInstanceOf[EditText].getText.toString
    val tuer = findViewById(R.id.eT_anTuer).asInstanceOf[EditText].getText.toString
    val plz = findViewById(R.id.eT_anPLZ).asInstanceOf[EditText].getText.toString
    val ort = findViewById(R.id.eT_anOrt).asInstanceOf[EditText].getText.toString
    val bundesland = findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].getSelectedItem().toString()
    val rb_auslandJa = findViewById(R.id.rB_anAuslandJa).asInstanceOf[RadioButton]
    val rb_HWSJa = findViewById(R.id.rB_anHWSJa).asInstanceOf[RadioButton]
    val ausland = if (rb_auslandJa.isChecked == true) "ja" else "nein"
    val hws = if (rb_HWSJa.isChecked == true) "ja" else "nein"
    val nameUG = findViewById(R.id.eT_anNameUG).asInstanceOf[EditText].getText.toString

    val anmeldeDaten: AnmeldeDaten = AnmeldeDaten(person_id, strasse, hausnummer, stiege, tuer, plz, ort, bundesland, ausland, hws, nameUG)

    val anmDao = db.mkAnmDao()
    anmDao.insert(anmeldeDaten)

    if (hws == "ja"){
      val hwsDaten: HauptwohnsitzDaten = HauptwohnsitzDaten(person_id, strasse, hausnummer, stiege, tuer, plz, ort, bundesland)
      val hwsDao = db.mkHwsDao()
      hwsDao.insert(hwsDaten)
    }
  }

  def updateData(view: View) = {
    val strasse = findViewById(R.id.eT_anStraße).asInstanceOf[EditText].getText.toString
    val hausnummer = findViewById(R.id.eT_anHausNr).asInstanceOf[EditText].getText.toString
    val stiege = findViewById(R.id.eT_anStiege).asInstanceOf[EditText].getText.toString
    val tuer = findViewById(R.id.eT_anTuer).asInstanceOf[EditText].getText.toString
    val plz = findViewById(R.id.eT_anPLZ).asInstanceOf[EditText].getText.toString
    val ort = findViewById(R.id.eT_anOrt).asInstanceOf[EditText].getText.toString
    val bundesland = findViewById(R.id.s_anBundesland).asInstanceOf[Spinner].getSelectedItem().toString()
    val rb_auslandJa = findViewById(R.id.rB_anAuslandJa).asInstanceOf[RadioButton]
    val rb_HWSJa = findViewById(R.id.rB_anHWSJa).asInstanceOf[RadioButton]
    val ausland = if (rb_auslandJa.isChecked == true) "ja" else "nein"
    val hws = if (rb_HWSJa.isChecked == true) "ja" else "nein"
    val nameUG = findViewById(R.id.eT_anNameUG).asInstanceOf[EditText].getText.toString

    val anmeldeDaten: AnmeldeDaten = AnmeldeDaten(person_id, strasse, hausnummer, stiege, tuer, plz, ort, bundesland, ausland, hws, nameUG)

    val anmDao = db.mkAnmDao()
    anmDao.insert(anmeldeDaten)

    if (hws == "ja") {
      val hwsDaten: HauptwohnsitzDaten = HauptwohnsitzDaten(person_id, strasse, hausnummer, stiege, tuer, plz, ort, bundesland)
      val hwsDao = db.mkHwsDao()
      hwsDao.update(hwsDaten, person_id)
    }
  }

  def gotoNext(view:View): Unit ={
    val check: String = getIntent.getExtras.get("update").asInstanceOf[String]
    val rb_HWSja = findViewById(R.id.rB_anHWSJa).asInstanceOf[RadioButton]
    val i = if (rb_HWSja.isChecked() == false) new Intent(this, classOf[HauptwohnsitzActivity]) else new Intent(this, classOf[ErfolgreichActivity])
    i.putExtra("person_id", person_id)

    if (check == "update") updateData(view) else saveData(view)

    finish()
    startActivity(i)
  }

  def goBack(view:View): Unit ={
    val i = new Intent(this, classOf[EntscheidungActivity])
    i.putExtra("person_id", person_id)
    finish()
    startActivity(i)
  }

  def fillAllSpinner(): Unit ={
    fillSpinner(findViewById(R.id.s_anBundesland).asInstanceOf[Spinner], Array("Steiermark", "Kärnten", "Burgenland", "Tirol", "Vorarlberg", "Salzburg", "Niederösterreich", "Oberösterreich", "Wien"))

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