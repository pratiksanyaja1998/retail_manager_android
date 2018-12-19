package retailmanager.spyhunter272.in.retailmanager.template;

import android.content.Context;

import java.io.BufferedWriter;

public class DefaultTemplate {


    public static void header(BufferedWriter bf) throws Exception{
        //this block const
        bf.append("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><meta charset=\"UTF-8\"><style type=\"text/css\" media=\"all\">\n" +
                "    \ttd{padding: 5px;}table{width: 100%;}.bgcolor{background-color: #dbdde0 !important;}.tcenter{text-align: center;}\n" +
                "    \tbody{-webkit-print-color-adjust:exact;}@page {size: auto;margin: 0; }.btnprint{margin: 10px;padding: 10px;}.border{border: 1px solid #a6a7a8;}.ucase {text-transform: uppercase;}</style>\n" +
                "    <style type=\"text/css\" media=\"print\">.btnprint{display: none;}</style><script type=\"text/javascript\">function printPage(obj){window.print();}</script>\n" +
                "</head><body style=\"width:95%\">");
        //btn print
        // bf.append("<div id=\"hide\"><input class=\"btnprint\" name=\"\" value=\"PRINT\" onclick=\"printPage(this)\" type=\"button\"></div>");
    }

    public static void brandDetails(Context context, BufferedWriter bf)  throws Exception{
//        bf.append("<table class=\"border bgcolor\">\n" +
//                "\t<tr><td width=\"70%\" align=\"center\"><span class=\"ucase\"><b><h1 style=\"margin-bottom:-10px \">"+sp.getString(context.getResources().getString(R.string.cname),"")+"</h1></b></span><small><br>"+sp.getString(context.getResources().getString(R.string.caddress),"")+
//                ",&nbsp;"+sp.getString(context.getResources().getString(R.string.ccity),"")+",&nbsp;"+sp.getString(context.getResources().getString(R.string.cstate),"")+"&nbsp;-&nbsp;"+sp.getString(context.getResources().getString(R.string.cpincode),"")+"<br>Gstin Number : "+sp.getString(context.getResources().getString(R.string.cgstion),"")+"</small></td></tr>\n" +
//
//                "</table>\n" +
//                "<table class=\"border\">\n" +
//                "\t<tr><td>Mobile Number : "+sp.getString(context.getResources().getString(R.string.cmo),"")+"</td><td align=\"right\">Invoice Date : "+dd+"-"+mm+"-"+yyyy+"</td></tr>\n" +
//                "\t<tr><td>Invoice Serial Number: "+sp.getString(context.getResources().getString(R.string.sno_prefix),"")+""+serialinvoise+"</td><td align=\"right\">Tax Is Payable On Reverse Charge: "+((tprcharge==1)?"Yes" : "No") +"</td></tr>\n" +
//                "</table><br>");
    }

    public static void customer(BufferedWriter bf)  throws Exception{
//        bf.append("<table>\n" +
//                "<tr class=\"bgcolor\"><td class=\"border\">Details of Receiver (Billed to)</td></tr>\n" +
//                "<tr><td class=\"border\">\n" +
//                "\t<table>\n" +
//                "\t<tr><td>Name: &nbsp;&nbsp;&nbsp;&nbsp;"+custdetails.getString("name")+"</td></tr>\n");
//
//        if(custdetails.getString("mo")!=null && !custdetails.getString("mo").equals(""))bf.append("\t<tr><td>Mobile Number :&nbsp;&nbsp;&nbsp;&nbsp;"+custdetails.getString("mo")+"</td></tr>\n");
//        if(custdetails.getString("area")!=null && !custdetails.getString("area").equals(""))bf.append("\t<tr><td>Address :&nbsp;&nbsp;&nbsp;&nbsp;"+custdetails.getString("area","")+","+custdetails.getString("city","")+", "+custdetails.getString("state","")+"-"+custdetails.getString("pincode","")+"</td> </tr>\n");
//        if(custdetails.getString("gstin")!=null && !custdetails.getString("gstin").equals(""))bf.append("\t<tr><td>GSTIN Number :&nbsp;&nbsp;&nbsp;&nbsp;"+custdetails.getString("gstin")+"</td></tr>\n" );
//
//        bf.append("\t</table></td></tr>\n" +
//                "</table>");
    }

    public static void products(BufferedWriter bf)  throws Exception{

//        //product header const
//        bf.append("<br><table class=\"border\">\n" +
//                "<tr class=\"bgcolor\"><td>S.No</td><td>Description of Goods</td><td>HSN Code</td><td>RATE</td><td>QTY</td>");
//
//        if(taxtype==0)bf.append("<td>SGST</td><td>CGST</td>");
//        else bf.append("<td>IGST</td>");
//
//        bf.append("<td>AMOUNT</td></tr>");
//
//        for (int i=0;i<proLists.size();i++) {
//            //body var
//            bf.append("<tr><td>"+(i+1)+"</td><td>"+proLists.get(i).name+"</td><td>"+proLists.get(i).hsn+"</td><td>"+proLists.get(i).amt+"</td><td>"+proLists.get(i).qty+"</td>");
//
//            if(taxtype==0)bf.append("<td>"+(proLists.get(i).tex/2)+"</td><td>"+(proLists.get(i).tex/2)+"</td>");
//            else
//                bf.append("<td>"+(proLists.get(i).tex)+"</td>");
//
//            bf.append("<td>"+(proLists.get(i).totalamt)+"</td></tr>");
//        }
//        //footer total desc more.. var
//        int i1=7,i2=6;
//
//        if (taxtype==1){
//            i1=6;
//            i2=5;
//        }
//
//        double amt=(des*totalamt)/100;
//
//
//        bf.append("<tr class=\"bgcolor\"><td colspan=\""+i1+"\">DISSCOUNT(%)</td><td> "+des+" %</td>\t\t\t\n" +
//                "</tr><tr class=\"bgcolor\"><td colspan=\""+i1+"\">TOTAL</td><td>Rs. "+(totalamt-amt)+" /-</td></tr>\n" +
//                "<tr class=\"bgcolor\"><td colspan=\"2\">TOTAL(in words)</td><td colspan=\""+i2+"\"></td></tr>\n" +
//                "</table>\n<br>");
    }

    public static void footer(BufferedWriter bf)  throws Exception{
//        bf.append("<table><tr class=\"bgcolor tcenter\"><td class=\"border\">Payment Mode</td><td class=\"border\">Electronic Reference Number</td></tr>\n" +
//                "<tr class=\"tcenter\"><td class=\"border\">"+((ptype==1) ? "Cash": (ptype==2)? "Bank/Card/Chaque":"Other")+"</td><td class=\"border\">&nbsp;</td></tr></table>\n" +
//                "<table><tbody><tr class=\"bgcolor tcenter\"><td class=\"border\">TERM &amp; CONDITION</td><td class=\"border\">"+sp.getString(String.valueOf(R.string.cname),"")+"</td></tr><tr class=\"tcenter\"><td class=\"border\">&nbsp; <br><br><br><br></td><td class=\"border\"><br><br>Signature:_______________</td></tr></table>\n" +
//                "</body>\n" +
//                "</html>");
    }


}
