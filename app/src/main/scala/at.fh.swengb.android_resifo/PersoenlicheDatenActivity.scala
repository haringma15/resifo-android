package at.fh.swengb.android_resifo

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.view.View
import android.widget._
import scala.util.matching.Regex

/**
  * Created by Martin on 15.01.2017.
  */
class PersoenlicheDatenActivity extends Activity{

  var db: Db = _
  var person_id = 0
  val d = new Data

  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.persoenliche_daten)

    person_id = getIntent.getExtras.get("person_id").asInstanceOf[Int]

    fillAllSpinner()

    db = Db(getApplicationContext)
    val dataMap = d.fillPersDaten(db, person_id)
    fillDataInTextView(dataMap, person_id)
  }

  def fillDataInTextView(personData: Map[Int, Any], person_id: Int) : Unit = {
    val arrGeburtsdatum = personData(person_id).asInstanceOf[Person].getGeburtsdatum()
    val geschlecht = personData(person_id).asInstanceOf[Person].getGeschlecht()
    val religion = personData(person_id).asInstanceOf[Person].getReligion()
    val familienstand = personData(person_id).asInstanceOf[Person].getFamilienstand()
    val staatsgehoerigkeit = personData(person_id).asInstanceOf[Person].getStaatsangehoerigkeit()

    findViewById(R.id.eT_nachname).asInstanceOf[TextView].setText(personData(person_id).asInstanceOf[Person].getNachname())
    findViewById(R.id.eT_vorname).asInstanceOf[TextView].setText(personData(person_id).asInstanceOf[Person].getVorname())
    findViewById(R.id.eT_nachnameVorher).asInstanceOf[TextView].setText(personData(person_id).asInstanceOf[Person].getNachnameAlt())

    //findViewById(R.id.rB_gebDatum).asInstanceOf[TextView].setText(personData(person_id).asInstanceOf[Person].getGeburtsdatum())
    findViewById(R.id.eT_gebOrt).asInstanceOf[TextView].setText(personData(person_id).asInstanceOf[Person].getGeburtsort())

    if(geschlecht == "m") {
      findViewById(R.id.rB_m).asInstanceOf[RadioButton].setChecked(true)
      findViewById(R.id.rB_w).asInstanceOf[RadioButton].setChecked(false)
    } else {
      findViewById(R.id.rB_m).asInstanceOf[RadioButton].setChecked(false)
      findViewById(R.id.rB_w).asInstanceOf[RadioButton].setChecked(true)
    }

    if(religion == "Christentum") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(0)
    } else if(religion == "Judentum") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(1)
    } else if(religion == "Islam") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(2)
    } else if(religion == "Hinduismus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(3)
    } else if(religion == "Buddhismus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(4)
    } else if(religion == "Taoismus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(5)
    } else if(religion == "Atheismus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(6)
    } else if(religion == "Sikhismus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(7)
    } else if(religion == "Mormonentum") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(8)
    } else if(religion == "Juche") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(9)
    } else if(religion == "Spiritimus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(10)
    } else if(religion == "Baha'i") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(11)
    } else if(religion == "Jainismus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(12)
    } else if(religion == "Shinto") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(13)
    } else if(religion == "Cao Dai") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(14)
    } else if(religion == "Tenrikyo") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(15)
    } else if(religion == "Anhänger Cthulus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(16)
    } else if(religion == "Pastafarianismus") {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(17)
    } else {
      findViewById(R.id.s_religion).asInstanceOf[Spinner].setSelection(18)
    }

    if(familienstand == "ledig") {
      findViewById(R.id.s_famStand).asInstanceOf[Spinner].setSelection(0)
    } else if(familienstand == "verheiratet") {
      findViewById(R.id.s_famStand).asInstanceOf[Spinner].setSelection(1)
    } else if(familienstand == "verwitwet") {
      findViewById(R.id.s_famStand).asInstanceOf[Spinner].setSelection(2)
    } else if(familienstand == "geschieden") {
      findViewById(R.id.s_famStand).asInstanceOf[Spinner].setSelection(3)
    } else if(familienstand == "Ehe aufgehoben") {
      findViewById(R.id.s_famStand).asInstanceOf[Spinner].setSelection(4)
    } else if(familienstand == "in Lebenspartnerschaft") {
      findViewById(R.id.s_famStand).asInstanceOf[Spinner].setSelection(5)
    } else {
      findViewById(R.id.s_famStand).asInstanceOf[Spinner].setSelection(6)
    }

    if(staatsgehoerigkeit == "Österreich") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(0)
    } else if (staatsgehoerigkeit == "Deutschland") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(1)
    } else if (staatsgehoerigkeit == "Schweiz") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(2)
    } else if (staatsgehoerigkeit == "Italien") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(3)
    } else if (staatsgehoerigkeit == "Tschechien") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(4)
    } else if (staatsgehoerigkeit == "Ungarn") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(5)
    } else if (staatsgehoerigkeit == "Slowakei") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(6)
    } else if (staatsgehoerigkeit == "Slowenien") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(7)
    } else if (staatsgehoerigkeit == "Liechtenstein") {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(8)
    } else {
      findViewById(R.id.s_staat).asInstanceOf[Spinner].setSelection(9)
    }
  }

  def saveData(view:View): Unit = {
    val nachname = findViewById(R.id.eT_nachname).asInstanceOf[EditText].getText.toString
    val vorname = findViewById(R.id.eT_vorname).asInstanceOf[EditText].getText.toString
    val nachnameVorher = findViewById(R.id.eT_nachnameVorher).asInstanceOf[EditText].getText.toString
    val gebTag = findViewById(R.id.s_gebTag).asInstanceOf[Spinner].getSelectedItem.toString
    val gebMonat = findViewById(R.id.s_gebMonat).asInstanceOf[Spinner].getSelectedItem.toString
    val gebJahr = findViewById(R.id.s_gebJahr).asInstanceOf[Spinner].getSelectedItem.toString
    val gebDatum = checkDate(gebTag, gebMonat, gebJahr)
    val gebOrt = findViewById(R.id.eT_gebOrt).asInstanceOf[EditText].getText.toString
    val rb_m = findViewById(R.id.rB_m).asInstanceOf[RadioButton]
    val geschlecht = if (rb_m.isChecked == true) "m" else "w"
    val religion = findViewById(R.id.s_religion).asInstanceOf[Spinner].getSelectedItem.toString
    val famStand = findViewById(R.id.s_famStand).asInstanceOf[Spinner].getSelectedItem.toString
    val staat = findViewById(R.id.s_staat).asInstanceOf[Spinner].getSelectedItem.toString

    val persDaten: PersoenlicheDaten = PersoenlicheDaten(nachname, vorname, nachnameVorher, gebDatum, gebOrt, geschlecht, religion, famStand, staat)

    val persDao = db.mkPersDao()
    persDao.insert(persDaten)
  }

  def fetchPersonId(): Int = {

    var someCursor: Option[Cursor] = None
    var id = 0

    try {
      someCursor = Option(db.getReadableDatabase.query("person", Array("person_id", "nachname", "vorname", "nachnameAlt", "geburtsdatum", "geburtsort", "geschlecht", "religion", "familienstand", "staatsangehoerigkeit"), null, null, null, null, null))
      someCursor match {
        case None =>
          System.err.println("Could not execute query due to some reason")
          id
        case Some(c) =>
          while (c.moveToNext()) {
            val pers_id = c.getInt(c.getColumnIndex("person_id"))
            id += pers_id
          }
          id
      }
    } finally {
      someCursor foreach (_.close())
    }
  }

  def updateData(view: View): Unit = {
    val nachname = findViewById(R.id.eT_nachname).asInstanceOf[EditText].getText.toString
    val vorname = findViewById(R.id.eT_vorname).asInstanceOf[EditText].getText.toString
    val nachnameVorher = findViewById(R.id.eT_nachnameVorher).asInstanceOf[EditText].getText.toString
    val gebTag = findViewById(R.id.s_gebTag).asInstanceOf[Spinner].getSelectedItem.toString
    val gebMonat = findViewById(R.id.s_gebMonat).asInstanceOf[Spinner].getSelectedItem.toString
    val gebJahr = findViewById(R.id.s_gebJahr).asInstanceOf[Spinner].getSelectedItem.toString
    val gebDatum = checkDate(gebTag, gebMonat, gebJahr)
    val gebOrt = findViewById(R.id.eT_gebOrt).asInstanceOf[EditText].getText.toString
    val rb_m = findViewById(R.id.rB_m).asInstanceOf[RadioButton]
    val geschlecht = if (rb_m.isChecked == true) "m" else "w"
    val religion = findViewById(R.id.s_religion).asInstanceOf[Spinner].getSelectedItem.toString
    val famStand = findViewById(R.id.s_famStand).asInstanceOf[Spinner].getSelectedItem.toString
    val staat = findViewById(R.id.s_staat).asInstanceOf[Spinner].getSelectedItem.toString

    val persDaten: PersoenlicheDaten = PersoenlicheDaten(nachname, vorname, nachnameVorher, gebDatum, gebOrt, geschlecht, religion, famStand, staat)

    val persDao = db.mkPersDao()
    persDao.update(persDaten, person_id)
  }

  def gotoNext(view:View): Unit ={
    val check: String = getIntent.getExtras.get("update").asInstanceOf[String]
    val doubleCheck: String = getIntent.getExtras.get("update2").asInstanceOf[String]
    if (check == "update") {
      updateData(view)
      if (doubleCheck == "update2"){
        val i = new Intent(this, classOf[EntscheidungActivity])
        i.putExtra("person_id", fetchPersonId())
        finish()
        startActivity(i)
      }
      else{
        val i = new Intent(this, classOf[ErfolgreichActivity])
        finish()
        startActivity(i)
      }
    }
    else {
      saveData(view)
      val i = new Intent(this, classOf[EntscheidungActivity])
      i.putExtra("person_id", fetchPersonId())
      finish()
      startActivity(i)
    }
  }

  def goBack(view:View): Unit = {
    val check: String = getIntent.getExtras.get("update").asInstanceOf[String]
    if (check == "update"){
      val i = new Intent(this, classOf[OverviewActivity])
      i.putExtra("person_id", person_id)
      finish()
      startActivity(i)
    }
    else{
      val i = new Intent(this, classOf[MainActivity])
      finish()
      startActivity(i)
    }
  }

  def fillAllSpinner(): Unit ={
    fillSpinner(findViewById(R.id.s_gebTag).asInstanceOf[Spinner], Array.range(1,31 + 1).map(x => x.toString))
    fillSpinner(findViewById(R.id.s_gebMonat).asInstanceOf[Spinner], Array.range(1,12 + 1).map(x => x.toString))
    fillSpinner(findViewById(R.id.s_gebJahr).asInstanceOf[Spinner], Array.range(1970,2015 + 1).reverse.map(x => x.toString))
    fillSpinner(findViewById(R.id.s_religion).asInstanceOf[Spinner], Array("Christentum", "Judentum", "Islam", "Hinduismus", "Buddhismus", "Taoismus", "Atheismus", "Sikhismus", "Mormonentum", "Juche", "Spiritimus", "Baha'i", "Jainismus", "Shinto", "Cao Dai", "Tenrikyo", "Anhänger Cthulus", "Pastafarianismus", "keine der oben genannten"))
    fillSpinner(findViewById(R.id.s_famStand).asInstanceOf[Spinner], Array("ledig", "verheiratet", "verwitwet", "geschieden", "Ehe aufgehoben", "in Lebenspartnerschaft", "nicht bekannt"))
    fillSpinner(findViewById(R.id.s_staat).asInstanceOf[Spinner], Array("Österreich", "Deutschland", "Schweiz", "Italien", "Tschechien", "Ungarn", "Slowakei", "Slowenien", "Liechtenstein", "keiner der oben genannten"))

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
/*
  def checkText(name: String): String = {
    val check: Regex = ".*\d.*".r
    name match {
      case `check` => name.replace("1","i").replace("2","z").replace("3","e").replace("4","a").replace("5","s").replace("6","g").replace("7","t").replace("8","b").replace("9","p").replace("0","o")
      case _ => name
    }
  }
  */
}
