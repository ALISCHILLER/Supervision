package com.msa.supervisor.tool.datetime
/**
 * create by Ali Soleymani.
 */
enum class DateFormat {
    /**
     * example: 1987/04/01
     */
    Date,
    /**
     * example: 10:20:30
     */
    Time,
    /**
     * example: 1987/04/01 10:20:30
     */
    Complete,
    /**
     * example: 1987-04-01T00:00:00.00
     */
    MicrosoftDateTime,
    /**
     * example: 19870401
     */
    Simple,
    /**
     * example: 20180128T013856_276
     */
    FileName
}