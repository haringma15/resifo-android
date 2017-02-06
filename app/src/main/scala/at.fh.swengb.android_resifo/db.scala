package at.fh.swengb.android_resifo

import android.content.{ContentValues, Context}
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

/**
  * Created by Sabine on 16.01.2017.
  * Updated a lot.
  * Really.
  */
case class Db(context: Context) extends SQLiteOpenHelper(context, "mydb", null, 1) {

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit = ()

  override def onCreate(db: SQLiteDatabase): Unit = {
    //Tabelle Person
    val personDao = SqlitePersDao(db)
    personDao.init()

    //Tabelle Anmeldung
    val anmDao = SqliteAnmDao(db)
    anmDao.init()

    //Tabelle Hauptsitz
    val hwsDao = SqliteHwsDao(db)
    hwsDao.init()

    //Tabelle Abmeldung
    val abmDao = SqliteAbmDao(db)
    abmDao.init()

    //Tabelle Fremde (Reisedokumente)
    val fremDao = SqliteFremDao(db)
    fremDao.init()

  }

  def mkPersDao(): SqlitePersDao = SqlitePersDao(getWritableDatabase)
  def mkAnmDao(): SqliteAnmDao = SqliteAnmDao(getWritableDatabase)
  def mkHwsDao(): SqliteHwsDao = SqliteHwsDao(getWritableDatabase)
  def mkAbmDao(): SqliteAbmDao = SqliteAbmDao(getWritableDatabase)
  def mkFremDao(): SqliteFremDao = SqliteFremDao(getWritableDatabase)
}

case class SqlitePersDao(db: SQLiteDatabase) extends BaseDao[PersoenlicheDaten]{
  def init(): Unit = db.execSQL("create table person " +
                                "(person_id INTEGER PRIMARY KEY ASC, " +
                                "nachname TEXT, " +
                                "vorname TEXT, " +
                                "nachnameAlt TEXT, " +
                                "geburtsdatum TEXT, " +
                                "geburtsort TEXT, " +
                                "geschlecht CHAR, " +
                                "religion TEXT, " +
                                "familienstand TEXT, " +
                                "staatsangehoerigkeit TEXT);")

  def insert(p: PersoenlicheDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("person", null, cv)
  }

  def mkContentValues(p: PersoenlicheDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("nachname", p.nachname)
    cv.put("vorname", p.vorname)
    cv.put("nachnameAlt", p.nachnameVorher)
    cv.put("geburtsdatum", p.gebDatum)
    cv.put("geburtsort", p.gebOrt)
    cv.put("geschlecht", p.geschlecht)
    cv.put("religion", p.religion)
    cv.put("familienstand", p.famStand)
    cv.put("staatsangehoerigkeit", p.staat)
    cv
  }

  def deleteById(id: Int): Int =  db.delete("person", "person_id = ?", Array(id.toString))

  def update(p: PersoenlicheDaten, id: Int): Int = db.update("person", mkContentValues(p), "person_id =? ", Array(id.toString))
}

case class SqliteAnmDao(db: SQLiteDatabase) extends BaseDao[AnmeldeDaten]{
  def init(): Unit = db.execSQL("create table anmeldung " +
                                "(anmeldung_id INTEGER PRIMARY KEY ASC, " +
                                "person_id INTEGER, " +
                                "strasse TEXT, " +
                                "hausnr INTEGER, " +
                                "stiege INTERGER, " +
                                "tuer INTERGER, " +
                                "plz INTEGER, " +
                                "ort TEXT, " +
                                "bundesland TEXT, " +
                                "zuzugAusAusland TEXT, " +
                                "hauptwohnsitz TEXT, " +
                                "unterkunftgeber TEXT); ");

  def insert(p: AnmeldeDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("anmeldung", null, cv)
  }

  def mkContentValues(p: AnmeldeDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("person_id", p.person_id.toString)
    cv.put("strasse", p.strasse)
    cv.put("hausnr", p.hausnummer)
    cv.put("stiege", p.stiege)
    cv.put("tuer", p.tuer)
    cv.put("plz", p.plz)
    cv.put("ort", p.ort)
    cv.put("bundesland", p.bundesland)
    cv.put("zuzugAusAusland", p.zuzugAusAusland)
    cv.put("hauptwohnsitz", p.hauptwohnsitz)
    cv.put("unterkunftgeber", p.unterkunftgeber)
    cv
  }

  def deleteById(id: Int): Int = db.delete("anmeldung", "person_id = ?", Array(id.toString))

  def update(p: AnmeldeDaten, id: Int): Int = db.update("anmeldung", mkContentValues(p), "person_id =? ", Array(id.toString))
}

case class SqliteHwsDao(db: SQLiteDatabase) extends BaseDao[HauptwohnsitzDaten]{
  def init(): Unit = db.execSQL("create table hauptsitz " +
                                " (hauptsitz_id INTEGER PRIMARY KEY ASC, " +
                                "person_id INTEGER, " +
                                "strasse TEXT, " +
                                "hausnr INTEGER, " +
                                "stiege INTERGER, " +
                                "tuer INTERGER, " +
                                "plz INTEGER, " +
                                "ort TEXT, " +
                                "bundesland TEXT); ");

  def insert(p: HauptwohnsitzDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("hauptsitz", null, cv)
  }

  def mkContentValues(p: HauptwohnsitzDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("person_id", p.person_id.toString)
    cv.put("strasse", p.strasse)
    cv.put("hausnr", p.hausnummer)
    cv.put("stiege", p.stiege)
    cv.put("tuer", p.tuer)
    cv.put("plz", p.plz)
    cv.put("ort", p.ort)
    cv.put("bundesland", p.bundesland)
    cv
  }

  def deleteById(id: Int): Int = db.delete("hauptsitz", "person_id = ?", Array(id.toString))

  def update(p: HauptwohnsitzDaten, id: Int): Int = db.update("hauptsitz", mkContentValues(p), "person_id =? ", Array(id.toString))
}

case class SqliteAbmDao(db: SQLiteDatabase) extends BaseDao[AbmeldeDaten]{
  def init(): Unit = db.execSQL("create table abmeldung" +
                                " (abmeldung_id INTEGER PRIMARY KEY ASC, " +
                                "person_id INTEGER, " +
                                "strasse TEXT, " +
                                "hausnr INTEGER, " +
                                "stiege INTERGER, " +
                                "tuer INTERGER, " +
                                "plz INTEGER, " +
                                "ort TEXT, " +
                                "bundesland TEXT, " +
                                "verzugInsAusland TEXT);")

  def insert(p: AbmeldeDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("abmeldung", null, cv)
  }

  def mkContentValues(p: AbmeldeDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("person_id", p.person_id.toString)
    cv.put("strasse", p.strasse)
    cv.put("hausnr", p.hausnummer)
    cv.put("stiege", p.stiege)
    cv.put("tuer", p.tuer)
    cv.put("plz", p.plz)
    cv.put("ort", p.ort)
    cv.put("bundesland", p.bundesland)
    cv.put("verzugInsAusland", p.zuzugAusAusland)
    cv
  }

  def deleteById(id: Int): Int = db.delete("abmeldung", "person_id = ?", Array(id.toString))

  def update(p: AbmeldeDaten, id: Int): Int = db.update("abmeldung", mkContentValues(p), "person_id =? ", Array(id.toString))
}

case class SqliteFremDao(db: SQLiteDatabase) extends BaseDao[FremdeDaten]{
  def init(): Unit = db.execSQL("create table fremde " +
    " (fremd_id INTEGER PRIMARY KEY ASC, " +
    "person_id INTEGER, " +
    "art TEXT, " +
    "nummer TEXT, " +
    "datum TEXT, " +
    "behoerde TEXT, " +
    "staatsangehoerigkeit TEXT);");

  def insert(p: FremdeDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("fremde", null, cv)
  }

  def mkContentValues(p: FremdeDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("art", p.art)
    cv.put("nummer", p.nummer)
    cv.put("datum", p.datum)
    cv.put("behoerde", p.behoerde)
    cv.put("staatsangehoerigkeit", p.staat)
    cv
  }

  def deleteById(id: Int): Int = db.delete("fremde", "person_id = ?", Array(id.toString))

  def update(p: FremdeDaten, id: Int): Int = db.update("fremde", mkContentValues(p), "person_id =? ", Array(id.toString))
}

trait BaseDao[T]{
  def insert(t: T): Long
}