package com.msa.supervisor.tool

import android.content.Context
import com.elvishew.xlog.BuildConfig
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import java.io.File
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */

/*
* <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
* */

class LoggerManager @Inject constructor(
    private val context: Context
) {

    //---------------------------------------------------------------------------------------------- init
    init {
        XLog.init( // Initialize XLog
            providerLoggerConfig(), // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
            providerLoggerAndroidPrinter(), // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
            providerLoggerConsolePrinter(),
            loggerFilePrinter()
        )
    }
    //---------------------------------------------------------------------------------------------- init



    //---------------------------------------------------------------------------------------------- getListOfFile
    fun getListOfFile() : Array<File> = loggerFile().listFiles() as Array<File>
    //---------------------------------------------------------------------------------------------- getListOfFile


    //---------------------------------------------------------------------------------------------- getLogOfFile
    fun getLogOfFile(file: File): String {
        val temp = file.bufferedReader()
        return temp.use { it.readText() }
    }
    //---------------------------------------------------------------------------------------------- getLogOfFile


    //---------------------------------------------------------------------------------------------- loggerFile
    private fun loggerFile() = File(context.cacheDir, "Msa")
    //---------------------------------------------------------------------------------------------- loggerFile


    //---------------------------------------------------------------------------------------------- providerLoggerConfig
    private fun providerLoggerConfig() = LogConfiguration.Builder()
        .logLevel(
            if (BuildConfig.DEBUG) LogLevel.ALL // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
            else LogLevel.ALL
        )
        .tag("X-LOG") // Specify TAG, default: "X-LOG"
        .enableStackTrace(1) // Enable stack trace info with depth 2, disabled by default
        .build()
    //---------------------------------------------------------------------------------------------- providerLoggerConfig


    //---------------------------------------------------------------------------------------------- providerLoggerAndroidPrinter
    private fun providerLoggerAndroidPrinter() = AndroidPrinter(true)
    //---------------------------------------------------------------------------------------------- providerLoggerAndroidPrinter


    //---------------------------------------------------------------------------------------------- providerLoggerConsolePrinter
    private fun providerLoggerConsolePrinter() = ConsolePrinter()
    //---------------------------------------------------------------------------------------------- providerLoggerConsolePrinter


    //---------------------------------------------------------------------------------------------- loggerFilePrinter
    private fun loggerFilePrinter() =
        FilePrinter.Builder(loggerFile().absolutePath) // Specify the directory path of log file(s)
            .fileNameGenerator(DateFileNameGenerator()) // Default: ChangelessFileNameGenerator("log")
            .backupStrategy(NeverBackupStrategy()) // Default: FileSizeBackupStrategy(1024 * 1024)
            .build()
    //---------------------------------------------------------------------------------------------- loggerFilePrinter

}