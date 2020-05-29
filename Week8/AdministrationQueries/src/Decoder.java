import java.util.Arrays;

public abstract class Decoder {
    /**
     * Converts a line from Data.txt to a string that is easier to work with.
     */
    public static String decode(String line){
        int pos = line.indexOf("{SPECIAL_PROPERTY}");
        String basicInfo = line.substring(0,pos-1);
        pos=line.indexOf("::");
        String specialProperty = line.substring(pos);

        String[] parts= splitBasicInfo(basicInfo);
        //for(String part: parts) System.out.println(part);
        //System.out.println("=".repeat(50));

        String[] parts2= splitSpecialProperty(specialProperty,parts[0].substring(parts[0].length()-1));
        //for (String part: parts2) System.out.println(part);
        //System.out.println("=".repeat(50));
        return Arrays.toString(parts)+ Arrays.toString(parts2);
    }

    /**
     * Splits all data entries except the special properties to array.
     */
    private static String[] splitBasicInfo(String basicInfo){
        String[] parts = basicInfo.split("@");
        for(int i=0;i<parts.length;i++){
            parts[i] = formatSingleProperty(parts[i]);
        }
        return parts;
    }

    /**
     * Splits all special properties into an array
     */
    private static String[] splitSpecialProperty(String specialProperty,String type){
        //System.out.println(specialProperty);
        String[] parts = specialProperty.substring(specialProperty.indexOf("::")+2).split(":");
        //for(String part: parts) System.out.println(part);
        switch (type){
            case "W":
            case "R": parts[0]= formatSingleProperty(parts[0]); break;
            case "M": for(int i=0;i<parts.length;i++) parts[i]=splitKids(parts[i]); break;
        }
        return parts;
    }

    /**
     * Formats a property into format propertyName:propertyValue
     */
    private static String formatSingleProperty(String specialProperty){
        return specialProperty.substring(1,specialProperty.lastIndexOf("}"))+":"+specialProperty.substring(specialProperty.indexOf("[")+1,specialProperty.lastIndexOf("]"));
    }

    /**
     * Slits and formats the special property kid into a String with format - KID FNAME:value KLNAME:value KAGE:value
     */
    private static String splitKids(String specialProperty){
        specialProperty = specialProperty.substring(specialProperty.indexOf("[")+1,specialProperty.length()-1);
        String[] parts = specialProperty.split("@");
        //for(String part: parts) System.out.println(part);
        for(int i=0;i<parts.length;i++) parts[i] = formatSingleProperty(parts[i]);

        StringBuilder toReturn = new StringBuilder();
        for (String part : parts) toReturn.append(part).append(" ");
        return "KID "+toReturn.toString();
    }

    /**
     * Decodes a query`s values into an array of Strings
     */
    public static String[] decodeQuery(String query){
        String queryBody = query.substring(6);
        if(queryBody.length()==0) {
            return new String[]{};
        }
        String[] parts;
        if(queryBody.contains("SPECIAL_PROPERTY")){
            queryBody = queryBody.substring(20);
            //System.out.println(queryBody);
            if(queryBody.contains("KID")){
                String temp =splitKids(queryBody);
                temp =temp.substring(4);
                //System.out.println(temp);
                return new String[]{temp };
            }else {
                return new String[]{formatSingleProperty(queryBody)};
            }
        }
        else{
            parts = splitBasicInfo(queryBody);
            //for (String part: parts) System.out.print(part+" ");
            //System.out.println();
        }
        return parts;
    }

}
