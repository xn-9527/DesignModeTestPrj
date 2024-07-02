package cn.test.jsonTest;

/**
 * @Author: Blackknight
 * @Date: 2024/7/2 12:00
 * @Description:
 */
public class JsonTimeCostTest {
    public static void main(String[] args) {
        long t = 0L;
        for (int i = 0; i < 100000; i++) {
            UserInfo userInfo = new JsonTimeCostTest().new UserInfo();
            userInfo.setName("name" + i);
            userInfo.setAge(i);
            userInfo.setAddress0("address0" + i);
            userInfo.setAddress1("address1" + i);
            userInfo.setAddress2("address2" + i);
            userInfo.setAddress3("address3" + i);
            userInfo.setAddress4("address4" + i);
            userInfo.setAddress5("address5" + i);
            userInfo.setAddress6("address6" + i);
            userInfo.setAddress7("address7" + i);
            userInfo.setAddress8("address8" + i);
            userInfo.setAddress9("address9" + i);
            userInfo.setAddress10("address10" + i);
            userInfo.setAddress11("address11" + i);
            userInfo.setAddress12("address12" + i);
            userInfo.setAddress13("address13" + i);
            userInfo.setAddress14("address14" + i);
            userInfo.setAddress15("address15" + i);
            userInfo.setAddress16("address16" + i);
            userInfo.setAddress17("address17" + i);
            userInfo.setAddress18("address18" + i);
            userInfo.setAddress19("address19" + i);
            userInfo.setAddress20("address20" + i);
            userInfo.setAddress21("address21" + i);
            userInfo.setAddress22("address22" + i);
            userInfo.setAddress23("address23" + i);
            userInfo.setAddress24("address24" + i);
            userInfo.setAddress25("address25" + i);
            userInfo.setAddress26("address26" + i);
            userInfo.setAddress27("address27" + i);
            userInfo.setAddress28("address28" + i);
            userInfo.setAddress29("address29" + i);
            userInfo.setAddress30("address30" + i);
            userInfo.setAddress31("address31" + i);
            userInfo.setAddress32("address32" + i);
            userInfo.setAddress33("address33" + i);
            userInfo.setAddress34("address34" + i);
            userInfo.setAddress35("address35" + i);
            userInfo.setAddress36("address36" + i);
            userInfo.setAddress37("address37" + i);
            userInfo.setAddress38("address38" + i);
            userInfo.setAddress39("address39" + i);
            userInfo.setAddress40("address40" + i);
            userInfo.setAddress41("address41" + i);
            userInfo.setAddress42("address42" + i);
            userInfo.setAddress43("address43" + i);
            userInfo.setAddress44("address44" + i);
            userInfo.setAddress45("address45" + i);
            userInfo.setAddress46("address46" + i);
            userInfo.setAddress47("address47" + i);
            userInfo.setAddress48("address48" + i);
            userInfo.setAddress49("address49" + i);
            userInfo.setAddress50("address50" + i);
            userInfo.setAddress51("address51" + i);
            userInfo.setAddress52("address52" + i);
            userInfo.setAddress53("address53" + i);
            userInfo.setAddress54("address54" + i);
            userInfo.setAddress55("address55" + i);
            userInfo.setAddress56("address56" + i);
            userInfo.setAddress57("address57" + i);
            userInfo.setAddress58("address58" + i);
            userInfo.setAddress59("address59" + i);
            long start = System.currentTimeMillis();
            String json = com.alibaba.fastjson.JSON.toJSONString(userInfo);
            long end = System.currentTimeMillis();
            System.out.println("data: " + json);
            t += end - start;
        }

        long t1 = 0L;
        for (int i = 0; i < 100000; i++) {
            UserInfo userInfo = new JsonTimeCostTest().new UserInfo();
            userInfo.setName("name" + i);
            userInfo.setAge(i);
            userInfo.setAddress0("address0" + i);
            userInfo.setAddress1("address1" + i);
            userInfo.setAddress2("address2" + i);
            userInfo.setAddress3("address3" + i);
            userInfo.setAddress4("address4" + i);
            userInfo.setAddress5("address5" + i);
            userInfo.setAddress6("address6" + i);
            userInfo.setAddress7("address7" + i);
            userInfo.setAddress8("address8" + i);
            userInfo.setAddress9("address9" + i);
            userInfo.setAddress10("address10" + i);
            userInfo.setAddress11("address11" + i);
            userInfo.setAddress12("address12" + i);
            userInfo.setAddress13("address13" + i);
            userInfo.setAddress14("address14" + i);
            userInfo.setAddress15("address15" + i);
            userInfo.setAddress16("address16" + i);
            userInfo.setAddress17("address17" + i);
            userInfo.setAddress18("address18" + i);
            userInfo.setAddress19("address19" + i);
            userInfo.setAddress20("address20" + i);
            userInfo.setAddress21("address21" + i);
            userInfo.setAddress22("address22" + i);
            userInfo.setAddress23("address23" + i);
            userInfo.setAddress24("address24" + i);
            userInfo.setAddress25("address25" + i);
            userInfo.setAddress26("address26" + i);
            userInfo.setAddress27("address27" + i);
            userInfo.setAddress28("address28" + i);
            userInfo.setAddress29("address29" + i);
            userInfo.setAddress30("address30" + i);
            userInfo.setAddress31("address31" + i);
            userInfo.setAddress32("address32" + i);
            userInfo.setAddress33("address33" + i);
            userInfo.setAddress34("address34" + i);
            userInfo.setAddress35("address35" + i);
            userInfo.setAddress36("address36" + i);
            userInfo.setAddress37("address37" + i);
            userInfo.setAddress38("address38" + i);
            userInfo.setAddress39("address39" + i);
            userInfo.setAddress40("address40" + i);
            userInfo.setAddress41("address41" + i);
            userInfo.setAddress42("address42" + i);
            userInfo.setAddress43("address43" + i);
            userInfo.setAddress44("address44" + i);
            userInfo.setAddress45("address45" + i);
            userInfo.setAddress46("address46" + i);
            userInfo.setAddress47("address47" + i);
            userInfo.setAddress48("address48" + i);
            userInfo.setAddress49("address49" + i);
            userInfo.setAddress50("address50" + i);
            userInfo.setAddress51("address51" + i);
            userInfo.setAddress52("address52" + i);
            userInfo.setAddress53("address53" + i);
            userInfo.setAddress54("address54" + i);
            userInfo.setAddress55("address55" + i);
            userInfo.setAddress56("address56" + i);
            userInfo.setAddress57("address57" + i);
            userInfo.setAddress58("address58" + i);
            userInfo.setAddress59("address59" + i);
            long start = System.currentTimeMillis();
            String json = userInfo.toString();
            long end = System.currentTimeMillis();
            System.out.println("data: " + json);
            t1 += end - start;
        }
        System.out.println("############fastjson time cost: " + t + ",avg: " + t / 100000.0 + "ms");
        System.out.println("############toString time cost: " + t1 + ",avg: " + t1 / 100000.0 + "ms");
    }
    public class UserInfo {
        private String name;
        private int age;
        private String address0;
        private String address1;
        private String address2;
        private String address3;
        private String address4;
        private String address5;
        private String address6;
        private String address7;
        private String address8;
        private String address9;
        private String address10;
        private String address11;
        private String address12;
        private String address13;
        private String address14;
        private String address15;
        private String address16;
        private String address17;
        private String address18;
        private String address19;
        private String address20;
        private String address21;
        private String address22;
        private String address23;
        private String address24;
        private String address25;
        private String address26;
        private String address27;
        private String address28;
        private String address29;
        private String address30;
        private String address31;
        private String address32;
        private String address33;
        private String address34;
        private String address35;
        private String address36;
        private String address37;
        private String address38;
        private String address39;
        private String address40;
        private String address41;
        private String address42;
        private String address43;
        private String address44;
        private String address45;
        private String address46;
        private String address47;
        private String address48;
        private String address49;
        private String address50;
        private String address51;
        private String address52;
        private String address53;
        private String address54;
        private String address55;
        private String address56;
        private String address57;
        private String address58;
        private String address59;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress0() {
            return address0;
        }

        public void setAddress0(String address0) {
            this.address0 = address0;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getAddress4() {
            return address4;
        }

        public void setAddress4(String address4) {
            this.address4 = address4;
        }

        public String getAddress5() {
            return address5;
        }

        public void setAddress5(String address5) {
            this.address5 = address5;
        }

        public String getAddress6() {
            return address6;
        }

        public void setAddress6(String address6) {
            this.address6 = address6;
        }

        public String getAddress7() {
            return address7;
        }

        public void setAddress7(String address7) {
            this.address7 = address7;
        }

        public String getAddress8() {
            return address8;
        }

        public void setAddress8(String address8) {
            this.address8 = address8;
        }

        public String getAddress9() {
            return address9;
        }

        public void setAddress9(String address9) {
            this.address9 = address9;
        }

        public String getAddress10() {
            return address10;
        }

        public void setAddress10(String address10) {
            this.address10 = address10;
        }

        public String getAddress11() {
            return address11;
        }

        public void setAddress11(String address11) {
            this.address11 = address11;
        }

        public String getAddress12() {
            return address12;
        }

        public void setAddress12(String address12) {
            this.address12 = address12;
        }

        public String getAddress13() {
            return address13;
        }

        public void setAddress13(String address13) {
            this.address13 = address13;
        }

        public String getAddress14() {
            return address14;
        }

        public void setAddress14(String address14) {
            this.address14 = address14;
        }

        public String getAddress15() {
            return address15;
        }

        public void setAddress15(String address15) {
            this.address15 = address15;
        }

        public String getAddress16() {
            return address16;
        }

        public void setAddress16(String address16) {
            this.address16 = address16;
        }

        public String getAddress17() {
            return address17;
        }

        public void setAddress17(String address17) {
            this.address17 = address17;
        }

        public String getAddress18() {
            return address18;
        }

        public void setAddress18(String address18) {
            this.address18 = address18;
        }

        public String getAddress19() {
            return address19;
        }

        public void setAddress19(String address19) {
            this.address19 = address19;
        }

        public String getAddress20() {
            return address20;
        }

        public void setAddress20(String address20) {
            this.address20 = address20;
        }

        public String getAddress21() {
            return address21;
        }

        public void setAddress21(String address21) {
            this.address21 = address21;
        }

        public String getAddress22() {
            return address22;
        }

        public void setAddress22(String address22) {
            this.address22 = address22;
        }

        public String getAddress23() {
            return address23;
        }

        public void setAddress23(String address23) {
            this.address23 = address23;
        }

        public String getAddress24() {
            return address24;
        }

        public void setAddress24(String address24) {
            this.address24 = address24;
        }

        public String getAddress25() {
            return address25;
        }

        public void setAddress25(String address25) {
            this.address25 = address25;
        }

        public String getAddress26() {
            return address26;
        }

        public void setAddress26(String address26) {
            this.address26 = address26;
        }

        public String getAddress27() {
            return address27;
        }

        public void setAddress27(String address27) {
            this.address27 = address27;
        }

        public String getAddress28() {
            return address28;
        }

        public void setAddress28(String address28) {
            this.address28 = address28;
        }

        public String getAddress29() {
            return address29;
        }

        public void setAddress29(String address29) {
            this.address29 = address29;
        }

        public String getAddress30() {
            return address30;
        }

        public void setAddress30(String address30) {
            this.address30 = address30;
        }

        public String getAddress31() {
            return address31;
        }

        public void setAddress31(String address31) {
            this.address31 = address31;
        }

        public String getAddress32() {
            return address32;
        }

        public void setAddress32(String address32) {
            this.address32 = address32;
        }

        public String getAddress33() {
            return address33;
        }

        public void setAddress33(String address33) {
            this.address33 = address33;
        }

        public String getAddress34() {
            return address34;
        }

        public void setAddress34(String address34) {
            this.address34 = address34;
        }

        public String getAddress35() {
            return address35;
        }

        public void setAddress35(String address35) {
            this.address35 = address35;
        }

        public String getAddress36() {
            return address36;
        }

        public void setAddress36(String address36) {
            this.address36 = address36;
        }

        public String getAddress37() {
            return address37;
        }

        public void setAddress37(String address37) {
            this.address37 = address37;
        }

        public String getAddress38() {
            return address38;
        }

        public void setAddress38(String address38) {
            this.address38 = address38;
        }

        public String getAddress39() {
            return address39;
        }

        public void setAddress39(String address39) {
            this.address39 = address39;
        }

        public String getAddress40() {
            return address40;
        }

        public void setAddress40(String address40) {
            this.address40 = address40;
        }

        public String getAddress41() {
            return address41;
        }

        public void setAddress41(String address41) {
            this.address41 = address41;
        }

        public String getAddress42() {
            return address42;
        }

        public void setAddress42(String address42) {
            this.address42 = address42;
        }

        public String getAddress43() {
            return address43;
        }

        public void setAddress43(String address43) {
            this.address43 = address43;
        }

        public String getAddress44() {
            return address44;
        }

        public void setAddress44(String address44) {
            this.address44 = address44;
        }

        public String getAddress45() {
            return address45;
        }

        public void setAddress45(String address45) {
            this.address45 = address45;
        }

        public String getAddress46() {
            return address46;
        }

        public void setAddress46(String address46) {
            this.address46 = address46;
        }

        public String getAddress47() {
            return address47;
        }

        public void setAddress47(String address47) {
            this.address47 = address47;
        }

        public String getAddress48() {
            return address48;
        }

        public void setAddress48(String address48) {
            this.address48 = address48;
        }

        public String getAddress49() {
            return address49;
        }

        public void setAddress49(String address49) {
            this.address49 = address49;
        }

        public String getAddress50() {
            return address50;
        }

        public void setAddress50(String address50) {
            this.address50 = address50;
        }

        public String getAddress51() {
            return address51;
        }

        public void setAddress51(String address51) {
            this.address51 = address51;
        }

        public String getAddress52() {
            return address52;
        }

        public void setAddress52(String address52) {
            this.address52 = address52;
        }

        public String getAddress53() {
            return address53;
        }

        public void setAddress53(String address53) {
            this.address53 = address53;
        }

        public String getAddress54() {
            return address54;
        }

        public void setAddress54(String address54) {
            this.address54 = address54;
        }

        public String getAddress55() {
            return address55;
        }

        public void setAddress55(String address55) {
            this.address55 = address55;
        }

        public String getAddress56() {
            return address56;
        }

        public void setAddress56(String address56) {
            this.address56 = address56;
        }

        public String getAddress57() {
            return address57;
        }

        public void setAddress57(String address57) {
            this.address57 = address57;
        }

        public String getAddress58() {
            return address58;
        }

        public void setAddress58(String address58) {
            this.address58 = address58;
        }

        public String getAddress59() {
            return address59;
        }

        public void setAddress59(String address59) {
            this.address59 = address59;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address0='" + address0 + '\'' +
                    ", address1='" + address1 + '\'' +
                    ", address2='" + address2 + '\'' +
                    ", address3='" + address3 + '\'' +
                    ", address4='" + address4 + '\'' +
                    ", address5='" + address5 + '\'' +
                    ", address6='" + address6 + '\'' +
                    ", address7='" + address7 + '\'' +
                    ", address8='" + address8 + '\'' +
                    ", address9='" + address9 + '\'' +
                    ", address10='" + address10 + '\'' +
                    ", address11='" + address11 + '\'' +
                    ", address12='" + address12 + '\'' +
                    ", address13='" + address13 + '\'' +
                    ", address14='" + address14 + '\'' +
                    ", address15='" + address15 + '\'' +
                    ", address16='" + address16 + '\'' +
                    ", address17='" + address17 + '\'' +
                    ", address18='" + address18 + '\'' +
                    ", address19='" + address19 + '\'' +
                    ", address20='" + address20 + '\'' +
                    ", address21='" + address21 + '\'' +
                    ", address22='" + address22 + '\'' +
                    ", address23='" + address23 + '\'' +
                    ", address24='" + address24 + '\'' +
                    ", address25='" + address25 + '\'' +
                    ", address26='" + address26 + '\'' +
                    ", address27='" + address27 + '\'' +
                    ", address28='" + address28 + '\'' +
                    ", address29='" + address29 + '\'' +
                    ", address30='" + address30 + '\'' +
                    ", address31='" + address31 + '\'' +
                    ", address32='" + address32 + '\'' +
                    ", address33='" + address33 + '\'' +
                    ", address34='" + address34 + '\'' +
                    ", address35='" + address35 + '\'' +
                    ", address36='" + address36 + '\'' +
                    ", address37='" + address37 + '\'' +
                    ", address38='" + address38 + '\'' +
                    ", address39='" + address39 + '\'' +
                    ", address40='" + address40 + '\'' +
                    ", address41='" + address41 + '\'' +
                    ", address42='" + address42 + '\'' +
                    ", address43='" + address43 + '\'' +
                    ", address44='" + address44 + '\'' +
                    ", address45='" + address45 + '\'' +
                    ", address46='" + address46 + '\'' +
                    ", address47='" + address47 + '\'' +
                    ", address48='" + address48 + '\'' +
                    ", address49='" + address49 + '\'' +
                    ", address50='" + address50 + '\'' +
                    ", address51='" + address51 + '\'' +
                    ", address52='" + address52 + '\'' +
                    ", address53='" + address53 + '\'' +
                    ", address54='" + address54 + '\'' +
                    ", address55='" + address55 + '\'' +
                    ", address56='" + address56 + '\'' +
                    ", address57='" + address57 + '\'' +
                    ", address58='" + address58 + '\'' +
                    ", address59='" + address59 + '\'' +
                    '}';
        }
    }
}
