package at.fh.swengb.android_resifo

import android.database.Cursor
import android.widget.EditText
import android.app.Activity
import android.view.View

/**
  * Created by Martin on 20.01.2017.
  */
class Data {

  def getDataIntoOverview(db: Db, i: Int):Map[String, Map[Int, Any]] = {
    var dataMap: Map[String, Map[Int, Any]] = Map()

    dataMap = dataMap + ("person" -> fillPersDaten(db, i))
    dataMap = dataMap + ("hauptwohnsitz" -> fillHwsDaten(db, i))
 //   dataMap = dataMap + ("anmeldung" -> fillAnmeldeDaten(db, i))
 //   dataMap = dataMap + ("abmeldung" -> fillAbmeldeDaten(db, i))
    dataMap
  }

  def getDataIntoList(db: Db):Map[Int, List[Any]] = {

    var someCursor: Option[Cursor] = None
    var someCursorHw: Option[Cursor] = None
    var dataMap: Map[Int, List[Any]] = Map()

    try {
      someCursor = Option(db.getReadableDatabase.query("person", Array("person_id", "nachname", "vorname"), null, null, null, null, null))
      someCursor match {
        case None => System.err.println("Could not execute query for some reason")
          Map()
        case Some(c) =>
          while (c.moveToNext()) {
            val personClass = new Person()
            val hauptsitzClass = new Hauptwohnsitz()

            personClass.setPersonId(c.getInt(c.getColumnIndex("person_id")))
            personClass.setNachname(c.getString(c.getColumnIndex("nachname")))
            personClass.setVorname(c.getString(c.getColumnIndex("vorname")))

            someCursorHw = Option(db.getReadableDatabase.query("hauptsitz", Array("hauptsitz_id", "person_id", "strasse", "hausnr", "plz", "ort"), "person_id = ?", Array(personClass.getPersonId().toString), null, null, null))
            someCursorHw match {
              case None => System.err.println("Could not execute query for some reason")
              case Some(cHw) =>
                while (cHw.moveToNext()) {
                  hauptsitzClass.setHauptsitzId(cHw.getInt(cHw.getColumnIndex("hauptsitz_id")))
                  hauptsitzClass.setStrasse(cHw.getString(cHw.getColumnIndex("strasse")))
                  hauptsitzClass.setHausnr(cHw.getString(cHw.getColumnIndex("hausnr")))
                  hauptsitzClass.setPlz(cHw.getString(cHw.getColumnIndex("plz")))
                  hauptsitzClass.setOrt(cHw.getString(cHw.getColumnIndex("ort")))
                }
            }
            dataMap = dataMap + (personClass.getPersonId() -> List(personClass, hauptsitzClass))
          }
      }
      dataMap
    } finally {
      someCursor foreach (_.close())
      Map()
    }
  }

  def fillPersDaten(db: Db, i: Int):Map[Int, Person] = {
    var someCursor: Option[Cursor] = None
    val personClass = new Person()
    var dataMap: Map[Int, Person] = Map(i -> personClass)
    val where: String = "person_id = " + i

    if(i == 0) {
      dataMap
    }

    try {
      someCursor = Option(db.getReadableDatabase.query("person", Array("person_id", "nachname", "vorname", "nachnameAlt", "geburtsdatum", "geburtsort", "geschlecht", "religion", "familienstand", "staatsangehoerigkeit"), where, null, null, null, null))
      someCursor match {
        case None => System.err.println("Could not execute query due to some reason")
          dataMap
        case Some(c) =>
          while (c.moveToNext()) {
            val personClass = new Person()

            personClass.setPersonId(c.getInt(c.getColumnIndex("person_id")))
            personClass.setNachname(c.getString(c.getColumnIndex("nachname")))
            personClass.setVorname(c.getString(c.getColumnIndex("vorname")))
            personClass.setNachnameAlt(c.getString(c.getColumnIndex("nachnameAlt")))
            personClass.setGeburtsdatum(c.getString(c.getColumnIndex("geburtsdatum")))
            personClass.setGeburtsort(c.getString(c.getColumnIndex("geburtsort")))
            personClass.setGeschlecht(c.getString(c.getColumnIndex("geschlecht")))
            personClass.setReligion(c.getString(c.getColumnIndex("religion")))
            personClass.setFamilienstand(c.getString(c.getColumnIndex("familienstand")))
            personClass.setStaatsangehoerigkeit(c.getString(c.getColumnIndex("staatsangehoerigkeit")))

            dataMap = dataMap + (personClass.getPersonId() -> personClass)
          }
          dataMap
      }
    } finally {
      someCursor foreach (_.close())
      dataMap
    }
  }

  def fillAnmeldeDaten(db: Db, i: Int):Map[Int, Anmeldung]  = {
    var where:String = ""
    if(i != 0) {
      where = "person_id = " + i
    }

    var someCursor: Option[Cursor] = None
    val anmeldungClass = new Anmeldung()
    var dataMap: Map[Int, Anmeldung] = Map(i -> anmeldungClass)
    try {
      someCursor = Option(db.getReadableDatabase.query("anmeldung", Array("anmeldung_id", "person_id", "strasse", "hausnr", "stiege", "tuer", "plz", "ort", "bundesland", "zuzugAusAusland", "hauptwohnsitz", "unterkunftgeber"), where, null, null, null, null))

      someCursor match {
        case None => System.err.println("Could not execute query due to some reason")
          dataMap
        case Some(c) =>
          while (c.moveToNext()) {
            val anmeldungClass = new Anmeldung()

            anmeldungClass.setAnmeldungId(c.getInt(c.getColumnIndex("anmeldung_id")))
            anmeldungClass.setPersonId(c.getInt(c.getColumnIndex("person_id")))
            anmeldungClass.setStrasse(c.getString(c.getColumnIndex("strasse")))
            anmeldungClass.setHausnr(c.getString(c.getColumnIndex("hausnr")))
            anmeldungClass.setStiege(c.getString(c.getColumnIndex("stiege")))
            anmeldungClass.setTuer(c.getString(c.getColumnIndex("tuer")))
            anmeldungClass.setPlz(c.getString(c.getColumnIndex("plz")))
            anmeldungClass.setOrt(c.getString(c.getColumnIndex("ort")))
            anmeldungClass.setBundesland(c.getString(c.getColumnIndex("bundesland")))
            anmeldungClass.setZuzugAusAusland(c.getString(c.getColumnIndex("zuzugAusAusland")))
            anmeldungClass.setHauptwohnsitz(c.getString(c.getColumnIndex("hauptwohnsitz")))
            anmeldungClass.setUnterkunftgeber(c.getString(c.getColumnIndex("unterkunftgeber")))

            dataMap = dataMap + (anmeldungClass.getPersonId() -> anmeldungClass)
          }
          dataMap
      }

    } finally {
      someCursor foreach (_.close())
      dataMap
    }
  }

  def fillHwsDaten(db: Db, i: Int):Map[Int, Hauptwohnsitz]  = {
    var where:String = ""
    if(i != 0) {
      where = "person_id = " + i
    }

    var someCursor: Option[Cursor] = None
    val hauptsitzClass = new Hauptwohnsitz()
    var dataMap: Map[Int, Hauptwohnsitz] = Map(i -> hauptsitzClass)
    try {
      someCursor = Option(db.getReadableDatabase.query("hauptsitz", Array("hauptsitz_id", "person_id", "strasse", "hausnr", "stiege", "tuer", "plz", "ort", "bundesland"), where, null, null, null, null))

      someCursor match {
        case None => System.err.println("Could not execute query due to some reason")
          dataMap
        case Some(c) =>
          while (c.moveToNext()) {
            val hauptsitzClass = new Hauptwohnsitz()

            hauptsitzClass.setHauptsitzId(c.getInt(c.getColumnIndex("hauptsitz_id")))
            hauptsitzClass.setPersonId(c.getInt(c.getColumnIndex("person_id")))
            hauptsitzClass.setStrasse(c.getString(c.getColumnIndex("strasse")))
            hauptsitzClass.setHausnr(c.getString(c.getColumnIndex("hausnr")))
            hauptsitzClass.setStiege(c.getString(c.getColumnIndex("stiege")))
            hauptsitzClass.setTuer(c.getString(c.getColumnIndex("tuer")))
            hauptsitzClass.setPlz(c.getString(c.getColumnIndex("plz")))
            hauptsitzClass.setOrt(c.getString(c.getColumnIndex("ort")))
            hauptsitzClass.setBundesland(c.getString(c.getColumnIndex("bundesland"))
            )
            dataMap = dataMap + (hauptsitzClass.getPersonId() -> hauptsitzClass)
          }
          dataMap
      }

    } finally {
      someCursor foreach (_.close())
      val hauptsitzClass = new Hauptwohnsitz()
      return Map(i -> hauptsitzClass)
    }
  }

  def fillAbmeldeDaten(db: Db, i: Int):Map[Int, Abmeldung]  = {
    var where:String = ""
    if(i != 0) {
      where = "person_id = " + i
    }

    var someCursor: Option[Cursor] = None
    val abmeldungClass = new Abmeldung()
    var dataMap: Map[Int, Abmeldung] = Map(i -> abmeldungClass)
    try {
      someCursor = Option(db.getReadableDatabase.query("abmeldung", Array("abmeldung_id", "person_id", "strasse", "hausnr", "stiege", "tuer", "plz", "ort", "bundesland", "verzugInsAusland"), where, null, null, null, null))

      someCursor match {
        case None => System.err.println("Could not execute query due to some reason")
          dataMap
        case Some(c) =>
          while (c.moveToNext()) {
            val abmeldungClass = new Abmeldung()

            abmeldungClass.setAbmeldungId(c.getInt(c.getColumnIndex("abmeldung_id")))
            abmeldungClass.setPersonId(c.getInt(c.getColumnIndex("person_id")))
            abmeldungClass.setStrasse(c.getString(c.getColumnIndex("strasse")))
            abmeldungClass.setHausnr(c.getString(c.getColumnIndex("hausnr")))
            abmeldungClass.setStiege(c.getString(c.getColumnIndex("stiege")))
            abmeldungClass.setTuer(c.getString(c.getColumnIndex("tuer")))
            abmeldungClass.setPlz(c.getString(c.getColumnIndex("plz")))
            abmeldungClass.setOrt(c.getString(c.getColumnIndex("ort")))
            abmeldungClass.setBundesland(c.getString(c.getColumnIndex("bundesland")))
            abmeldungClass.setVerzugAusAusland(c.getString(c.getColumnIndex("verzugInsAusland")))

            dataMap = dataMap + (abmeldungClass.getPersonId() -> abmeldungClass)
          }
          dataMap
      }

    } finally {
      someCursor foreach (_.close())
      dataMap
    }
  }

}
