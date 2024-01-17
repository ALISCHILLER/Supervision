package com.msa.supervisor.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.msa.supervisor.R
import com.msa.supervisor.model.data.response.report.ProductInvoiveBalanceReportModel
import com.msa.supervisor.tool.Currency
import java.math.BigDecimal
/**
 * create by Ali Soleymani.
 */
class TotalReportDialog {

    companion object{
        fun show(
            context: Context,
            date:String,
            delearText:String,
            invoiveBalanceReport: MutableList<ProductInvoiveBalanceReportModel>
        ){
            val alertDialog= AlertDialog.Builder(context).create()
            val inflater= LayoutInflater.from(context)
            val convertView=inflater.inflate(R.layout.dialog_total_report,null)
            alertDialog.setView(convertView)

            val btnClose = convertView.findViewById<TextView>(R.id.btnClose)
            val txtDate = convertView.findViewById<TextView>(R.id.txtDate)
            val delear = convertView.findViewById<TextView>(R.id.txtVisitor)
            val txtFinalAmountInvoice = convertView.findViewById<TextView>(R.id.txtFinalAmountInvoice)
            val txtInvoiceBalanceAmount = convertView.findViewById<TextView>(R.id.txtInvoiceBalanceAmount)
            val txtremittanceamount = convertView.findViewById<TextView>(R.id.txt_remittance_amount)
            val txtcashamount = convertView.findViewById<TextView>(R.id.txt_cash_amount)
            val txtcheckamount = convertView.findViewById<TextView>(R.id.txt_check_amount)
            val txtclearingamount = convertView.findViewById<TextView>(R.id.txt_clearing_amount)

            txtDate.text=date
            delear.text= delearText

            val clearingamount =
                getFinalAmountInvoiceReport(invoiveBalanceReport) { it.usancePaid }
            txtclearingamount.text = clearingamount

            val checkamount =
                getFinalAmountInvoiceReport(invoiveBalanceReport) { it.paidCheck }
            txtcheckamount.text = checkamount

            val remittanceamount =
                getFinalAmountInvoiceReport(invoiveBalanceReport) { it.paidPose }
            txtremittanceamount.text = remittanceamount

            val cashamount =
                getFinalAmountInvoiceReport(invoiveBalanceReport) { it.paidCash }
            txtcashamount.text =cashamount


            val invoiceFinalPrice =
                getFinalAmountInvoiceReport(invoiveBalanceReport) { it.invoiceFinalPrice }
            txtFinalAmountInvoice.text =context.getString(R.string.txtFinalAmountInvoice, invoiceFinalPrice)

            val invoiceBalanceAmount =
                getFinalAmountInvoiceReport(invoiveBalanceReport) { it.ivoiceRemain }
            txtInvoiceBalanceAmount.text =context.getString(R.string.invoiceBalanceAmount, invoiceBalanceAmount)



            btnClose.setOnClickListener {
                alertDialog.dismiss()
            }


            alertDialog.show()
        }

        fun getFinalAmountInvoiceReport(
            customerSales: MutableList<ProductInvoiveBalanceReportModel>,
            calculateTotal: (ProductInvoiveBalanceReportModel) -> BigDecimal
        ): String {
            var total = BigDecimal.ZERO
            for (item in customerSales) {
                total = total.add(calculateTotal(item))
            }
            return Currency(total).toFormattedString()
        }
    }

}