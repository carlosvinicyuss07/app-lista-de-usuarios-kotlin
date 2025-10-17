package com.example.recyclerviewapp

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS usuarios_new (
                localId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                idApi INTEGER,
                name TEXT NOT NULL,
                username TEXT NOT NULL,
                email TEXT NOT NULL,
                address TEXT,
                phone TEXT,
                website TEXT,
                company TEXT,
                origemLocal INTEGER NOT NULL DEFAULT 0
            )
        """)

        // Copia dados antigos para a nova tabela
        db.execSQL("""
            INSERT INTO usuarios_new (idApi, name, username, email, address, phone, website, company)
            SELECT id, name, username, email, address, phone, website, company FROM usuarios
        """)

        // Remove tabela antiga
        db.execSQL("DROP TABLE usuarios")

        // Renomeia a nova tabela para o nome original
        db.execSQL("ALTER TABLE usuarios_new RENAME TO usuarios")
    }
}