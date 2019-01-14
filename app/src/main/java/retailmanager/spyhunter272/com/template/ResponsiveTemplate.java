package retailmanager.spyhunter272.com.template;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

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

public class ResponsiveTemplate {

    public static String getTemplateData(Invoice invoice, Context context){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(header());
        stringBuffer.append(brandDetails(context,invoice));
        stringBuffer.append(customer(invoice));
        stringBuffer.append(products(invoice));
        stringBuffer.append(footer(invoice,context));
        return stringBuffer.toString();

    }

    public static String header(){


        return  "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Store Manager Printer</title>\n" +
                "\n" +
                "    <link rel=\"stylesheet\" media=\"all\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
                " <style>\n" +
                "        @media all {\n" +
                "\n" +
                "    @page {\n" +
                "        size: auto;margin: 0;\n" +
                "        }\n" +
                "\n" +
                "    @font-face {\n" +
                "        font-family: 'Material Icons';\n" +
                "        font-style: normal;\n" +
                "        font-weight: 400;\n" +
                "        src: url(\"iconFont.woff2\") format('woff2');\n" +
                "      }\n" +
                "      \n" +
                "      .material-icons {\n" +
                "        font-family: 'Material Icons';\n" +
                "        font-weight: normal;\n" +
                "        font-style: normal;\n" +
                "        font-size: 24px;\n" +
                "        line-height: 1;\n" +
                "        letter-spacing: normal;\n" +
                "        text-transform: none;\n" +
                "        display: inline-block;\n" +
                "        white-space: nowrap;\n" +
                "        word-wrap: normal;\n" +
                "        direction: ltr;\n" +
                "       \n" +
                "      }\n" +
                "   \n" +
                "    body {\n" +
                "        -webkit-print-color-adjust: exact;\n" +
                "        font-size:17px;\n" +
                "        margin: 0;\n" +
                "    }\n" +
                "\n" +
                "    b{\n" +
                "        font-weight: 600\n" +
                "    }\n" +
                "\n" +
                "    .bgcolor{\n" +
                "        background-color: #dbdde0 !important;\n" +
                "    }\n" +
                "\n" +
                "    .bgImage{\n" +
                "        background-image: url(file:///android_asset/bggray.jpg);\n" +
                "    }\n" +
                "\n" +
                "    .w3-custom-teal{\n" +
                "        color: white !important;\n" +
                "        background-color: rgba(13, 60, 74, 0.7)!important;\n" +
                "    }\n" +
                "\n" +
                "    .w3-custom-gray{\n" +
                "        color: rgba(68, 67, 67, 0.93);\n" +
                "        background-color: rgba(216, 212, 212, 0.65)!important;\n" +
                "    }\n" +
                "\n" +
                "    .w3-custom-teal-btn{\n" +
                "        background-color: rgba(13, 60, 74, 0.45)!important;\n" +
                "        color: white;\n" +
                "      }\n" +
                "\n" +
                "    .w3-custom-gray-text{\n" +
                "        color: rgba(68, 67, 67, 0.93);\n" +
                "        \n" +
                "    }\n" +
                "\n" +
                "    .theme-red{\n" +
                "        color: rgba(29, 16, 16, 0.93);\n" +
                "        background-color: rgba(197, 112, 112, 0.65)!important;\n" +
                "    }\n" +
                "\n" +
                "    .theme-red-text{\n" +
                "        color: rgba(29, 16, 16, 0.93);\n" +
                "    }\n" +
                "\n" +
                "      .income-label{\n" +
                "        background-color: rgba(37, 177, 199, 0.3);\n" +
                "        color: rgba(0, 0, 0, 0.71);\n" +
                "        border: 1px solid #11b8ea;\n" +
                "      }\n" +
                "      \n" +
                "      .expance-label{\n" +
                "        background-color: rgba(255, 0, 118, 0.3);\n" +
                "          color: rgba(0, 0, 0, 0.71);\n" +
                "          border: 1px solid rgba(185, 18, 134, 0.8);\n" +
                "      }\n" +
                "      \n" +
                "      .total-label{\n" +
                "        background-color: rgba(96, 148, 100, 0.3);\n" +
                "        color: rgba(0, 0, 0, 0.71);\n" +
                "        border: 1px solid #1b8c14;\n" +
                "      }\n" +
                "\n" +
                "    .font-15{\n" +
                "        font-size: 15px;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    .print-view-right{\n" +
                "        left:25%;   \n" +
                "        width: 75%;\n" +
                "        height: 100%;\n" +
                "        position: absolute;\n" +
                "        overflow-y:auto;\n" +
                "    }\n" +
                "\n" +
                "    .print-view-botoom{\n" +
                "        width: 100%\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    .controlLeftHome{\n" +
                "        width: 25%; height: 100%;position: absolute;\n" +
                "    }\n" +
                "\n" +
                "    .controlTopHome{\n" +
                "        width: 100%;\n" +
                "       \n" +
                "    }\n" +
                "\n" +
                "    </style>"+
                "\n" +

                "</head>\n" +
                "\n" +
                "<body  class=\"w3-light-grey\" >"+"<div class=\"w3-row w3-margin w3-card w3-padding w3-custom-gray-text\">\n";

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



        return  " <div class=\"w3-col l12 w3-custom-gray  w3-center w3-padding\">\n" +
                "\n" +
                "        <b><big>"+retailName+"</big>\n" +
                "            <br/>\n" +
                "            \n" +
                "                    "+address+"\n" +
                "                    , "+city+"\n" +
                "                     ,"+state+"\n" +
                "                     - "+pincode+"\n" +
                "\n" +
                "                    <br/>\n" +
                "                     GSTIN :-"+gstin+"\n" +
                "                    &nbsp;&nbsp;MO :- +91 "+mobile+"\n" +
                "                \n" +
                "            \n" +
                "        </b>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"w3-col l12 w3-margin-top w3-padding w3-custom-gray font-15\">\n" +
                "\n" +
                "        <div class=\"w3-left w3-col s4 m4 l4\">\n" +
                "            <span class=\"w3-left\">\n" +
                "                <b>INVOICE NO :-</b> "+invoice.getId()+"\n" +
                "            </span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"w3-center w3-col s4 m4 l4\">\n" +
                "            <span class=\"w3-center\" >\n" +
                "\n" +
//                "                <b>CHALAN NO :-</b> {{invoiceData.invoice.billno}}\n" +
                "            </span>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"w3-right w3-col s4 m4 l4\">\n" +
                "            <span class=\"w3-right\">\n" +
                "                <b>DATE :-</b> "+invoice.getDd()+" / "+invoice.getMm()+" / "+invoice.getYyyy()+"\n" +
                "            </span>\n" +
                "        </div>\n" +
                "    </div>\n";



    }

    public static String customer(Invoice invoice) {

        Customer customer = invoice.getCustomer();

        if(customer==null){
            return "";
        }

        StringBuffer bf = new StringBuffer();


        bf.append(" <div class=\"w3-col l12 w3-margin-top font-15\">\n" +
                "\n" +
                "        <div>\n" +
                "            <span >\n" +
                "                <b>&nbsp;&nbsp;&nbsp;Customer Name :-</b> "+customer.getName()+"\n" +
                "\n" +
                "            </span>\n" +
                "\n" +
                "            <span >\n" +
                "                <b><br/>&nbsp;&nbsp;&nbsp;Customer Mobile Number :- </b> +91 "+customer.getMobile()+" &nbsp;&nbsp;&nbsp;&nbsp;</span>\n" +
                "            <span >\n" +
                "                <b>&nbsp;&nbsp;&nbsp;Customer GSTIN :-</b> "+customer.getGstin()+"\n" +
                "\n" +
                "            </span>\n" +
                "        </div>");


        if(!customer.getBilling_address().isEmpty())
            bf.append("        <div class=\" \" style=\"width: 47%;display: inline-block;\" >\n" +
                    "            <div class=\"w3-custom-gray w3-padding w3-margin-top\">\n" +
                    "                <b>Billing Address</b>\n" +
                    "            </div>\n" +
                    "            <div class=\"w3-padding\" style=\"padding-top: 5px;padding-left: 5px\">\n" +
                    "                "+customer.getBilling_address().getStreet()+", "+ customer.getBilling_address().getCity()+","+customer.getBilling_address().getCity() +""+customer.getBilling_address().getPostCode() +"\n" +
                    "\n" +
                    "            </div>\n" +
                    "        </div>\n");

        if(customer.isIs_same_b_s()){
            bf.append("        <div class=\"   \" style=\"width: 2%;display: inline-block;\"></div>\n" +
                    "\n" +
                    "        <div class=\"  w3-right\" style=\"width: 48%;display: inline-block;\" >\n" +
                    "            <div class=\"w3-custom-gray w3-padding w3-margin-top\">\n" +
                    "                <b>Delivery Address</b>\n" +
                    "            </div>\n" +
                    "            <div class=\"w3-padding\" style=\"padding-top: 5px;padding-left: 5px\">\n" +
                    "                " + customer.getBilling_address().getStreet() + ", " + customer.getBilling_address().getCity() + "," + customer.getBilling_address().getCity() + "" + customer.getBilling_address().getPostCode() + "\n" +
                    "\n" +
                    "            </div>\n" +
                    "        </div>\n");

        }else if(!customer.getBilling_address().isEmpty()) {
            bf.append("        <div class=\"   \" style=\"width: 2%;display: inline-block;\"></div>\n" +
                    "\n" +
                    "        <div class=\"  w3-right\" style=\"width: 48%;display: inline-block;\" >\n" +
                    "            <div class=\"w3-custom-gray w3-padding w3-margin-top\">\n" +
                    "                <b>Delivery Address</b>\n" +
                    "            </div>\n" +
                    "            <div class=\"w3-padding\" style=\"padding-top: 5px;padding-left: 5px\">\n" +
                    "                " + customer.getShipping_address().getStreet() + ", " + customer.getShipping_address().getCity() + "," + customer.getShipping_address().getCity() + "" + customer.getShipping_address().getPostCode() + "\n" +
                    "\n" +
                    "            </div>\n" +
                    "        </div>\n");
        }


        bf.append("    </div>");


        return bf.toString();
    }

    public static String products(Invoice invoice){
        List<InvoiceProduct> proLists = invoice.getInvoiceProducts();

        StringBuffer bf = new StringBuffer();


        bf.append("    <div class=\"w3-col font-15 w3-margin-top\">\n");
        bf.append("<table class=\"w3-table w3-border-bottom  w3-centered  font-15\">\n" +
                "\n" +
                "            <tr class=\"w3-custom-gray\">\n" +
                "                <th>ID</th>\n" +
                "                <th>ITEMS</th>\n" +
                "                <th>HSN</th>\n" +
                "                <th>PRICE</th>\n" +
                "                <th>QTY</th>");

        if(invoice.getGsttype()==0)bf.append("<td>SGST</td><td>CGST</td>");
        else bf.append("<td>IGST</td>");

        bf.append("<td>TOTAL</td></tr>");

        for (int i=0;i<proLists.size();i++) {

            String hsn = proLists.get(i).getHsn(),
                    name= proLists.get(i).getName();
            double price = proLists.get(i).getPrice();

            if(hsn==null)
                hsn="";

            bf.append("<tr class=\"w3-custom-gray-text\" ><td>"+(i+1)+"</td><td>"+name+"</td><td>"+hsn+"</td><td>"+price+"</td><td>"+proLists.get(i).getQty()+"</td>");

            if(invoice.getGsttype()==0)bf.append("<td>"+(proLists.get(i).getGst()/2)+"</td><td>"+(proLists.get(i).getGst()/2)+"</td>");
            else
                bf.append("<td>"+(proLists.get(i).getGst())+"</td>");

            bf.append("<td>"+(proLists.get(i).getTotal())+"</td></tr>");
        }


        bf.append("        </table>\n");

        //footer total desc more.. var
        int i1=7,i2=6;

        if (invoice.getGsttype()==1){
            i1=6;
            i2=5;
        }

        double amt=(invoice.getDiscount()*invoice.getTotal())/100;



        bf.append(" <div class=\" font-15 \" style=\"margin-top: 5px\">\n" +
                "\n" +
                "\n" +
                "            <table class=\"font-15 w3-right w3-custom-gray-text\">\n" +
//                "                <tr >\n" +
//                "                    <td><b> (%) :-</b></td>\n" +
//                "                    <td class=\"w3-right\"><b> "" %</b></td>\n" +
//                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><b>DISCOUNT (%) :-</b></td>\n" +
                "                    <td class=\"w3-right\"><b>"+ invoice.getDiscount()+" %</b></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td><b>TOTAL :-</b></td>\n" +
                "                    <td class=\"w3-right\"><b>"+(Math.round(invoice.getTotal()-amt))+"  /-</b> </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "\n" +
                "        </div>\n" +
                "        <div class=\"w3-left\">\n" +
                "            <small>\n" +
                "                <b> "+NumberToWord.NTOW(Math.round(invoice.getTotal()-amt))+"</b>\n" +
                "            </small>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>");


        return bf.toString();

    }

    public static String footer(Invoice invoice,Context context) {

        SharedPreferences myPreference =PreferenceManager.getDefaultSharedPreferences(context);

        String   retailName = myPreference.getString(SP_KEY_FOR_RETAIL_INFO_NAME,"");

       return "    <div class=\"w3-col font-15 w3-margin-top\" >\n" +
               "        <div class=\"w3-custom-gray w3-padding \">\n" +
               "            <b>Other Details</b>\n" +
               "        </div>\n" +
               "        <div class=\"w3-padding\">\n" +
               "            <b>Payment Methord :-</b> "+invoice.getPaymentMethrd()+"\n" +
               "\n" +
               "            <span >\n" +
               "                <br/>\n" +
               "                <b>Tax In Reverse Change :-</b> "+invoice.isTprchage()+"</span>\n" +
               "\n" +
               "            <span >\n" +
               "                <br/><b>Desciption :-</b>"+invoice.getDesciption()+"</span>\n" +
               "\n" +
               "        </div>\n" +
               "    </div>\n" +
               "\n" +
               "\n" +
               "    <div class=\"w3-col font-15  w3-margin-top\">\n" +
               "        <div class=\"w3-custom-gray w3-padding \">\n" +
               "            <b>Terms & Condition</b>\n" +
               "        </div>\n" +
               "        <div class=\"w3-custom-gray-text w3-padding\">\n" +
               "            <small>\n" +
               "                1 . Goods once sold will not be taken back.\n" +
               "                <br/> 2 . Any breakage,leakage,damage,loss,expiry will not be returnable once sold.\n" +
               "\n" +
               "                <div class=\"w3-right\">\n" +
               "                    <b>Sing.____________________</b>\n" +
               "                </div>\n" +
               "            </small>\n" +
               "        </div>\n" +
               "    </div>\n" +
               "\n" +
               "</div>\n" +
               "\n" +
               "</body>\n" +
               "\n" +
               "</html>";

    }


}
