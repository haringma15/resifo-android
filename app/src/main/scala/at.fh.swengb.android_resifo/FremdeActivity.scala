package at.fh.swengb.android_resifo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.{ArrayAdapter, EditText, RadioButton, Spinner}

import scala.util.matching.Regex

/**
  * Created by Martin on 19.01.2017.
  */
class FremdeActivity extends Activity{

  var db: Db = _
  var person_id = 0

  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.reisedokument_fremde)

    db = Db(getApplicationContext)

    fillAllSpinner()

    person_id = getIntent.getExtras.get("person_id").asInstanceOf[Int]
  }

  def saveData(view:View): Unit = {
    val art = findViewById(R.id.s_rdArt).asInstanceOf[Spinner].getSelectedItem.toString
    val nummer = checkNumber(findViewById(R.id.eT_rdNummer).asInstanceOf[EditText].getText.toString)
    val rdTag = findViewById(R.id.s_rdTag).asInstanceOf[Spinner].getSelectedItem.toString
    val rdMonat = findViewById(R.id.s_rdMonat).asInstanceOf[Spinner].getSelectedItem.toString
    val rdJahr = findViewById(R.id.s_rdJahr).asInstanceOf[Spinner].getSelectedItem.toString
    val rdDatum = checkDate(rdTag, rdMonat, rdJahr)
    val behoerde = checkText(findViewById(R.id.eT_rdBehoerde).asInstanceOf[EditText].getText.toString)
    val staat = findViewById(R.id.s_rdStaat).asInstanceOf[Spinner].getSelectedItem.toString

    val fremdDaten: FremdeDaten = FremdeDaten(person_id, art, nummer, rdDatum, behoerde, staat)

    val fremDao = db.mkFremDao()
    fremDao.insert(fremdDaten)
  }

  def gotoNext(view: View): Unit ={
    saveData(view)
    val i = new Intent(this, classOf[ErfolgreichActivity])
    finish()
    startActivity(i)
  }

  def goBack(view:View): Unit ={
    val i = new Intent(this, classOf[AbmeldungActivity])
    i.putExtra("person_id", person_id)
    i.putExtra("update", "update")
    finish()
    startActivity(i)
  }

  def fillAllSpinner(): Unit ={
    fillSpinner(findViewById(R.id.s_rdArt).asInstanceOf[Spinner], Array("Reisepass", "Personalausweis", "keine der oben genannten"))
    fillSpinner(findViewById(R.id.s_rdTag).asInstanceOf[Spinner], Array.range(1,31 + 1).map(x => x.toString))
    fillSpinner(findViewById(R.id.s_rdMonat).asInstanceOf[Spinner], Array.range(1,31 + 1).map(x => x.toString))
    fillSpinner(findViewById(R.id.s_rdJahr).asInstanceOf[Spinner], Array.range(1970,2015 + 1).reverse.map(x => x.toString))
    fillSpinner(findViewById(R.id.s_rdStaat).asInstanceOf[Spinner], Array("USA", "Russland", "Brasilien", "Chile", "Skandinavien", "Alaska", "Kanada", "China", "Japan", "keiner der oben genannten"))

    def fillSpinner(spinner: Spinner, content: Array[String]): Unit ={
      val adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, content)
      spinner.setAdapter(adapter)
    }
  }

  def checkDate(day: String, month: String, year: String): String = {

    val date = s"$day.$month.$year"

    if (year.toInt % 4 == 0){
      if (month == "2") {
        if (day.toInt > 29) return s"29.2.$year"
      }
    }
    else {
      if (month == "2"){
        if (day.toInt > 28) return s"28.2.$year"
      }
      else if (month == "4" || month == "6" || month == "9" || month == "11") {
        if (day.toInt > 30) return s"30.$month.$year"
      }
    }
    date
  }

  def checkText(name: String): String = {
    val check = ".*\d.*".r
    name match {
      case `check` => name.replace("1","i").replace("2","z").replace("3","e").replace("4","a").replace("5","s").replace("6","g").replace("7","t").replace("8","b").replace("9","p").replace("0","o")
      case _ => name
    }
  }

  def checkNumber(number: String): String = {
    val check = ".*\s.*".r
    number match {
      case `check` => ""
      case _ => number
    }
  }
}
