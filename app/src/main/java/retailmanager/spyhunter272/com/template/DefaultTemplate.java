package retailmanager.spyhunter272.com.template;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

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
        stringBuffer.append(products(invoice,context));
        stringBuffer.append(footer(invoice,context));
        return stringBuffer.toString();

    }

    public static String header(){
        //this block const
        return  "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><meta charset=\"UTF-8\"><style type=\"text/css\" media=\"all\">\n" +
                "    \ttd{padding: 5px;}table{width: 100%;}.bgcolor{background-color: #dbdde0 !important;}.tcenter{text-align: center;}\n" +
                "    \tbody{-webkit-print-color-adjust:exact;}@page {size: auto;margin: 0; }.error{color: red;}.btnprint{margin: 10px;padding: 10px;}.border{border: 1px solid #a6a7a8;}.ucase {text-transform: uppercase;}</style>\n" +
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


        String prefix = myPreference.getString("invoicePre","SNOO");

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
                "\t<tr><td>Invoice Serial Number: "+prefix+invoice.getId()+"</td>"+
                (invoice.isTprchage() ? "<td align=\"right\">Tax Is Payable On Reverse Charge: "+((invoice.isTprchage())?"Yes" : "No") +"</td>":"")+"</tr>\n" +
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


        if( !customer.getBilling_address().isEmpty())
            bf.append("\t<tr><td>Billing Address :&nbsp;&nbsp;&nbsp;&nbsp;"+customer.getBilling_address().getStreet()+","+customer.getBilling_address().getCity()+", "+customer.getBilling_address().getState()+"-"+customer.getBilling_address().getPostCode()+"</td> </tr>\n");


        if(!customer.isIs_same_b_s()) {

            if ( !customer.getShipping_address().isEmpty())
                bf.append("\t<tr><td>Shipping Address :&nbsp;&nbsp;&nbsp;&nbsp;" + customer.getShipping_address().getStreet() + "," + customer.getShipping_address().getCity() + ", " + customer.getShipping_address().getState() + "-" + customer.getShipping_address().getPostCode() + "</td> </tr>\n");

        }else {

            bf.append("\t<tr><td>Shipping Address :&nbsp;&nbsp;&nbsp;&nbsp;"+customer.getBilling_address().getStreet()+","+customer.getBilling_address().getCity()+", "+customer.getBilling_address().getState()+"-"+customer.getBilling_address().getPostCode()+"</td> </tr>\n");

        }

        if(customer.getGstin()!=null && !customer.getGstin().equals(""))bf.append("\t<tr><td>GSTIN Number :&nbsp;&nbsp;&nbsp;&nbsp;"+customer.getGstin()+"</td></tr>\n" );

        bf.append("\t</table></td></tr></table>");

        return bf.toString();
    }

    public static String products(Invoice invoice, Context context){
        List<InvoiceProduct> proLists = invoice.getInvoiceProducts();
        boolean isHsn = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("hsn",true);
        StringBuffer bf = new StringBuffer();

//        Customer customer = invoice.getCustomer();

        if(invoice.getCustomer()!=null){
//            return "";
            bf.append("<br/>");
        }

        bf.append("<table class=\"border\"><tr class=\"bgcolor\"><td>S.No</td><td>Description of Goods</td>");

        if(isHsn)
            bf.append("<td>HSN Code</td>");

        bf.append("<td>RATE</td><td>QTY</td>");

        bf.append("<td>AMOUNT</td></tr>");

        for (int i=0;i<proLists.size();i++) {
            //body var
            String hsn = proLists.get(i).getHsn(),name= proLists.get(i).getName();
            double price = Math.round(proLists.get(i).getPrice());

            if(hsn==null)
                hsn="";


            bf.append("<tr><td>"+(i+1)+"</td><td>"+name+"</td>");
            if(isHsn)
                bf.append("<td>"+hsn+"</td>");
            bf.append("<td>"+price+"</td><td>"+proLists.get(i).getQty()+"</td>");
            bf.append("<td>"+(proLists.get(i).getTotal())+"</td></tr>");
        }

        double amt=invoice.getTotal();

        String gstrow, gstLabel, colspan="5" ;

        if(invoice.getGsttype()==0){
            gstLabel = "GST";
        }else {
            gstLabel = "IGST";
        }

        if(!isHsn)
            colspan="4";

        gstrow = "<tr class=\"bgcolor\"><td colspan=\""+colspan+"\">"+gstLabel+"(%)</td><td> "+invoice.getGst()+" %</td></tr>";


        bf.append("<tr class=\"bgcolor\"><td colspan=\""+colspan+"\">DISSCOUNT(%)</td><td> "+invoice.getDiscount()+" %</td></tr>" +
                gstrow+
                "<tr class=\"bgcolor\"><td colspan=\""+colspan+"\">TOTAL</td><td>Rs. "+(Math.round(invoice.getTotal()))+" /-</td></tr>\n" +
                "<tr class=\"bgcolor\"><td colspan=\"2\">TOTAL(in words)</td><td colspan=\""+4+"\">"+NumberToWord.NTOW(Math.round(invoice.getTotal())) +"</td></tr>\n" +
                "</table><br>");

        return bf.toString();
    }

    public static String footer(Invoice invoice,Context context) {

        SharedPreferences myPreference =PreferenceManager.getDefaultSharedPreferences(context);

        String retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME,"");

        return  "<table><tr class=\"bgcolor tcenter\"><td class=\"border\">Payment Mode</td><td class=\"border\">Electronic Reference Number</td></tr>\n" +
                "<tr class=\"tcenter\"><td class=\"border\">"+invoice.getPaymentMethrd()+"</td><td class=\"border\">&nbsp;</td></tr></table>\n" +
                "<table><tbody><tr class=\"bgcolor tcenter\"><td class=\"border\">TERM &amp; CONDITION</td><td class=\"border\">"+retailName+"</td></tr><tr class=\"tcenter\"><td class=\"border\">&nbsp; <br><br><br><br></td><td class=\"border\"><br><br>Signature:_______________</td></tr></table>\n" +
                "</body></html>";
    }


}
