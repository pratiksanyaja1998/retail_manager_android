package retailmanager.spyhunter272.com.template;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import java.io.BufferedWriter;
import java.util.List;

import retailmanager.spyhunter272.com.room.table.Customer;
import retailmanager.spyhunter272.com.room.table.Invoice;
import retailmanager.spyhunter272.com.room.table.InvoiceProduct;
import retailmanager.spyhunter272.com.utils.NumberToWord;

import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_ADDRESS;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_CITY;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_EMAIL;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_GSTIN;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_MOBILE;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_NAME;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_PINCODE;
import static retailmanager.spyhunter272.com.holder.RetailInformationHolder.SP_KEY_FOR_RETAIL_INFO_STATE;

public class DefaultTemplate {


    public static String getTemplateData(Invoice invoice,Context context){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(header());
        stringBuffer.append(brandDetails(context,invoice));
        stringBuffer.append(customer(invoice));
        stringBuffer.append(products(invoice));
        stringBuffer.append(footer(invoice,context));
        return stringBuffer.toString();

    }

    public static String header(){
        //this block const
       return  "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><meta charset=\"UTF-8\"><style type=\"text/css\" media=\"all\">\n" +
                "    \ttd{padding: 5px;}table{width: 100%;}.bgcolor{background-color: #dbdde0 !important;}.tcenter{text-align: center;}\n" +
                "    \tbody{-webkit-print-color-adjust:exact;}@page {size: auto;margin: 0; }.error{color: red;}.btnprint{margin: 10px;padding: 10px;}.border{border: 1px solid #a6a7a8;}.ucase {text-transform: uppercase;}</style>\n" +
                "    <style type=\"text/css\" media=\"print\"></style><script type=\"text/javascript\">function printPage(obj){window.print();}</script>\n" +
                "</head><body style=\"width:95%\">";
        //btn print
        // bf.append("<div id=\"hide\"><input class=\"btnprint\" name=\"\" value=\"PRINT\" onclick=\"printPage(this)\" type=\"button\"></div>");
    }

    public static String brandDetails(Context context,Invoice invoice) {


        SharedPreferences myPreference =PreferenceManager.getDefaultSharedPreferences(context);

        String   retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME,"");
        String  address = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_ADDRESS,"");
        String  gstin =myPreference.getString(SP_KEY_FOR_RETAIL_INFO_GSTIN,"");
        String   mobile = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_MOBILE,"");
        String   email = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_EMAIL,"");
        String city =myPreference.getString(SP_KEY_FOR_RETAIL_INFO_CITY,"");
        String state = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_STATE,"");
        String pincode = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_PINCODE,"");


        if(!gstin.equals("")){

            gstin =  "Gstin Number : "+gstin+"</small>";
        }

        if(retailName.equals("")){
            retailName ="<span class=\"error\">GOTO - Retail Information tab into home page and add retail detail.</span>";
        }


        return  "<table class=\"border bgcolor\">\n" +
                "\t<tr><td width=\"70%\" align=\"center\"><span class=\"ucase\"><b><h1 style=\"margin-bottom:-10px \">"+retailName+"</h1></b></span><small><br>"+address+
                ",&nbsp;"+city+",&nbsp;"+state+"&nbsp;-&nbsp;"+pincode+"<br>" +
                 gstin +
                "</td></tr>\n" +

                "</table>\n" +
                "<table class=\"border\">\n" +
                "\t<tr><td>Mobile Number : "+mobile+"</td><td align=\"right\">Invoice Date : "+invoice.getDd()+"-"+invoice.getMm()+"-"+invoice.getYyyy()+"</td></tr>\n" +
                "\t<tr><td>Invoice Serial Number: "+invoice.getId()+"</td><td align=\"right\">Tax Is Payable On Reverse Charge: "+((invoice.isTprchage())?"Yes" : "No") +"</td></tr>\n" +
                "</table><br>";
    }

    public static String customer(Invoice invoice) {

        Customer customer = invoice.getCustomer();

        if(customer==null){
            return "";
        }

        StringBuffer bf = new StringBuffer();

        bf.append("<table>\n" +
                "<tr class=\"bgcolor\"><td class=\"border\">Details of Receiver (Billed to)</td></tr>\n" +
                "<tr><td class=\"border\">\n" +
                "\t<table>\n" +
                "\t<tr><td>Name: &nbsp;&nbsp;&nbsp;&nbsp;"+customer.getName()+"</td></tr>\n");

        if(customer.getMobile()!=null && !customer.getMobile().equals(""))bf.append("\t<tr><td>Mobile Number :&nbsp;&nbsp;&nbsp;&nbsp;"+customer.getMobile()+"</td></tr>\n");
        if(customer.getBilling_address()!=null && !customer.getBilling_address().equals(""))bf.append("\t<tr><td>Address :&nbsp;&nbsp;&nbsp;&nbsp;"+customer.getBilling_address().getStreet()+","+customer.getBilling_address().getCity()+", "+customer.getBilling_address().getState()+"-"+customer.getBilling_address().getPostCode()+"</td> </tr>\n");
        if(customer.getGstin()!=null && !customer.getGstin().equals(""))bf.append("\t<tr><td>GSTIN Number :&nbsp;&nbsp;&nbsp;&nbsp;"+customer.getGstin()+"</td></tr>\n" );

        bf.append("\t</table></td></tr>\n" +
                "</table>");

        return bf.toString();
    }

    public static String products(Invoice invoice){
        List<InvoiceProduct> proLists = invoice.getInvoiceProducts();

        StringBuffer bf = new StringBuffer();

        //product header const
        bf.append("<br><table class=\"border\">\n" +
                "<tr class=\"bgcolor\"><td>S.No</td><td>Description of Goods</td><td>HSN Code</td><td>RATE</td><td>QTY</td>");

        if(invoice.getGsttype()==0)bf.append("<td>SGST</td><td>CGST</td>");
        else bf.append("<td>IGST</td>");

        bf.append("<td>AMOUNT</td></tr>");

        for (int i=0;i<proLists.size();i++) {
            //body var
            bf.append("<tr><td>"+(i+1)+"</td><td>"+proLists.get(i).getName()+"</td><td>"+proLists.get(i).getHsn()+"</td><td>"+proLists.get(i).getPrice()+"</td><td>"+proLists.get(i).getQty()+"</td>");

            if(invoice.getGsttype()==0)bf.append("<td>"+(proLists.get(i).getGst()/2)+"</td><td>"+(proLists.get(i).getGst()/2)+"</td>");
            else
                bf.append("<td>"+(proLists.get(i).getGst())+"</td>");

            bf.append("<td>"+(proLists.get(i).getTotal())+"</td></tr>");
        }
        //footer total desc more.. var
        int i1=7,i2=6;

        if (invoice.getGsttype()==1){
            i1=6;
            i2=5;
        }

        double amt=(invoice.getDiscount()*invoice.getTotal())/100;


        bf.append("<tr class=\"bgcolor\"><td colspan=\""+i1+"\">DISSCOUNT(%)</td><td> "+invoice.getDiscount()+" %</td>\t\t\t\n" +
                "</tr><tr class=\"bgcolor\"><td colspan=\""+i1+"\">TOTAL</td><td>Rs. "+(Math.round(invoice.getTotal()-amt))+" /-</td></tr>\n" +
                "<tr class=\"bgcolor\"><td colspan=\"2\">TOTAL(in words)</td><td colspan=\""+i2+"\">"+NumberToWord.NTOW(Math.round(invoice.getTotal()-amt)) +"</td></tr>\n" +
                "</table>\n<br>");

        return bf.toString();
    }

    public static String footer(Invoice invoice,Context context) {

        SharedPreferences myPreference =PreferenceManager.getDefaultSharedPreferences(context);

        String   retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME,"");

        return  "<table><tr class=\"bgcolor tcenter\"><td class=\"border\">Payment Mode</td><td class=\"border\">Electronic Reference Number</td></tr>\n" +
                "<tr class=\"tcenter\"><td class=\"border\">"+invoice.getPaymentMethrd()+"</td><td class=\"border\">&nbsp;</td></tr></table>\n" +
                "<table><tbody><tr class=\"bgcolor tcenter\"><td class=\"border\">TERM &amp; CONDITION</td><td class=\"border\">"+retailName+"</td></tr><tr class=\"tcenter\"><td class=\"border\">&nbsp; <br><br><br><br></td><td class=\"border\"><br><br>Signature:_______________</td></tr></table>\n" +
                "</body>\n" +
                "</html>";
    }


}
