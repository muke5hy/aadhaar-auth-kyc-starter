/*******************************************************************************
 * DISCLAIMER: The sample code or utility or tool described herein
 *    is provided on an "as is" basis, without warranty of any kind.
 *    UIDAI does not warrant or guarantee the individual success
 *    developers may have in implementing the sample code on their
 *    environment. 
 *
 *    UIDAI does not warrant, guarantee or make any representations
 *    of any kind with respect to the sample code and does not make
 *    any representations or warranties regarding the use, results
 *    of use, accuracy, timeliness or completeness of any data or
 *    information relating to the sample code. UIDAI disclaims all
 *    warranties, express or implied, and in particular, disclaims
 *    all warranties of merchantability, fitness for a particular
 *    purpose, and warranties related to the code, or any service
 *    or software related thereto. 
 *
 *    UIDAI is not responsible for and shall not be liable directly
 *    or indirectly for any direct, indirect damages or costs of any
 *    type arising out of use or any action taken by you or others
 *    related to the sample code.
 *
 *    THIS IS NOT A SUPPORTED SOFTWARE.
 ******************************************************************************/
package in.gov.uidai.core.device.helper;

import com.google.protobuf.ByteString;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import in.gov.uidai.core.device.model.DeviceCollectedAuthData;
import in.gov.uidai.core.model.protobuf.auth.Auth;
import in.gov.uidai.core.model.protobuf.auth.Auth.LangCode;
import in.gov.uidai.core.model.protobuf.auth.Auth.Position;
import in.gov.uidai.core.model.xsd.auth.uid_auth_request_data._1.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class provides utility method to create Pid object using data that was collected by the Auth Device
 * in the Auth Client GUI.
 *
 * @author UIDAI
 */
public class PidCreator {

  //public static Pid pidTs=null;
  public static final ThreadLocal<Pid> threadLocalPidTs = new ThreadLocal<Pid>();
  //public static in.gov.uidai.authserver.protobuf.Auth.Pid.Builder pidBuilderTs =  null;
  public static ThreadLocal<Auth.Pid.Builder> threadLocalPidBuilderTs = new ThreadLocal<Auth.Pid.Builder>();

  public static Pid createXmlPid(DeviceCollectedAuthData data) {

    Pid pid = new Pid();

    if (data != null) {

      boolean isPiPresent = false, isPaPresent = false, isPfaPresent = false;

      Demo demo = new Demo();

      demo.setLang(data.getLanguage());

      if (data.getName() != null && !data.getName().isEmpty()
          || data.getLname() != null && !data.getLname().isEmpty()
          || data.getDob() != null && !data.getDob().isEmpty()
          || data.getDobType() != null && !data.getDobType().isEmpty()
          || data.getEmail() != null && !data.getEmail().isEmpty()
          || data.getGender() != null && !data.getGender().equalsIgnoreCase("Select gender")
          || data.getAge() != null && !data.getAge().isEmpty()
          || data.getPhoneNo() != null && !data.getPhoneNo().isEmpty()) {
        Pi pi = new Pi();
        pi.setMs(data.getNameMatchStrategy());
        pi.setMv(data.getNameMatchValue());
        pi.setName(data.getName());

        if (data.getLname() != null && !data.getLname().isEmpty()) {
          pi.setLmv(data.getLocalNameMatchValue());
          pi.setLname(data.getLname());
        }

        pi.setDob(data.getDob());
        pi.setDobt(data.getDobType());

        if (StringUtils.isNumeric(data.getAge())) {
          pi.setAge(new Integer(data.getAge()));
        } else {
          if (StringUtils.isNotBlank(data.getAge())) {
            throw new RuntimeException("Age should be numeric");
          }
        }

        pi.setEmail(data.getEmail());
        if (data.getGender().equalsIgnoreCase("Male")) {
          pi.setGender(Gender.M);
        } else if (data.getGender().equalsIgnoreCase("Female")) {
          pi.setGender(Gender.F);
        } else if (data.getGender().equalsIgnoreCase("Transgender")) {
          pi.setGender(Gender.T);
        } else {
          pi.setGender(null);
        }
        pi.setPhone(data.getPhoneNo());
        demo.setPi(pi);

        isPiPresent = true;
      }

      if (!data.getFullAddress().isEmpty()
          || (data.getLocalFullAddress() != null && !data
          .getLocalFullAddress().isEmpty())) {
        Pfa pfa = new Pfa();
        pfa.setMs(data.getFullAddressMatchStrategy());
        pfa.setMv(data.getFullAddressMatchValue());
        pfa.setAv(data.getFullAddress());

        if (!data.getLocalFullAddress().isEmpty()) {
          pfa.setLav(data.getLocalFullAddress());
          pfa.setLmv(data.getLocalFullAddressMatchValue());
        }

        demo.setPfa(pfa);

        isPfaPresent = true;
      }
      // Add Pa only if one of the constituent attributes have a value
      // specified
      if (data.getCareOf() != null && !data.getCareOf().isEmpty()
          || data.getDistrict() != null && !data.getDistrict().isEmpty()
          || data.getBuilding() != null && !data.getBuilding().isEmpty()
          || data.getLandmark() != null && !data.getLandmark().isEmpty()
          || data.getLocality() != null && !data.getLocality().isEmpty()
          || data.getPinCode() != null && !data.getPinCode().isEmpty()
          || data.getPoName() != null && !data.getPoName().isEmpty()
          || data.getSubdistrict() != null && !data.getSubdistrict().isEmpty()
          || data.getState() != null && !data.getState().isEmpty()
          || data.getStreet() != null && !data.getStreet().isEmpty()
          || data.getVillage() != null
          && !data.getVillage().isEmpty()) {
        Pa pa = new Pa();
        pa.setMs(data.getAddressMatchStrategy());
        pa.setCo(data.getCareOf());
        pa.setDist(data.getDistrict());
        pa.setHouse(data.getBuilding());
        pa.setLm(data.getLandmark());
        pa.setLoc(data.getLocality());
        pa.setPc(data.getPinCode());
        pa.setPo(data.getPoName());
        pa.setSubdist(data.getSubdistrict());
        pa.setState(data.getState());
        pa.setStreet(data.getStreet());
        pa.setVtc(data.getVillage());
        demo.setPa(pa);

        isPaPresent = true;
      }

      if (isPiPresent || isPaPresent || isPfaPresent) {
        pid.setDemo(demo);
      }

      if (StringUtils.isNotBlank(data.getStaticPin()) || StringUtils.isNotBlank(data.getDynamicPin())) {
        pid.setPv(new Pv());
        pid.getPv().setPin(data.getStaticPin());
        pid.getPv().setOtp(data.getDynamicPin());
      }

      Calendar calendar = GregorianCalendar.getInstance();
      pid.setTs(XMLGregorianCalendarImpl.createDateTime(
          calendar.get(Calendar.YEAR),
          calendar.get(Calendar.MONTH) + 1,
          calendar.get(Calendar.DAY_OF_MONTH),
          calendar.get(Calendar.HOUR_OF_DAY),
          calendar.get(Calendar.MINUTE),
          calendar.get(Calendar.SECOND)));
      System.out.println("PID" + pid.getTs());
      if (data.getBiometrics() != null
          && data.getBiometrics().size() > 0) {

        Bios bios = new Bios();
        pid.setBios(bios);

        for (DeviceCollectedAuthData.BiometricData p : data.getBiometrics()) {
          Bio bio = new Bio();
          bio.setType(p.getType());
          bio.setValue(p.getBiometricContent());
          bio.setPosh(p.getPosition());
          pid.getBios().getBio().add(bio);
        }
      }
    }
    //pidTs = pid;
    threadLocalPidTs.set(pid);
    //System.err.println(pid);
    return pid;
  }


  public static Auth.Pid createProtoPid(DeviceCollectedAuthData data) {

    Auth.Pid.Builder pidBuilder = Auth.Pid.newBuilder();

    if (data != null) {
      Auth.Demo.Builder demo = Auth.Demo.newBuilder();

      if (data.getLanguage() != null) {
        demo.setLang(LangCode.valueOf(Integer.valueOf(data.getLanguage())));
      }

      boolean isPiPresent = false, isPaPresent = false, isPfaPresent = false;
      if (data.getName() != null && !data.getName().isEmpty()
          || data.getLname() != null && !data.getLname().isEmpty()
          || data.getDob() != null && !data.getDob().isEmpty()
          || data.getDobType() != null && !data.getDobType().isEmpty()
          || data.getEmail() != null && !data.getEmail().isEmpty()
          || data.getGender() != null && !data.getGender().equalsIgnoreCase("Select gender")
          || data.getAge() != null && !data.getAge().isEmpty()
          || data.getPhoneNo() != null && !data.getPhoneNo().isEmpty()) {

        Auth.Pi.Builder pi = Auth.Pi.newBuilder();

        if (data.getNameMatchStrategy().value().equalsIgnoreCase(Auth.Ms.E.name())) {
          pi.setMs(Auth.Ms.E);
        }
        if (data.getNameMatchStrategy().value().equalsIgnoreCase(Auth.Ms.P.name())) {
          pi.setMs(Auth.Ms.P);
        }
        if (data.getNameMatchStrategy().value().equalsIgnoreCase(Auth.Ms.F.name())) {
          pi.setMs(Auth.Ms.F);
        }
        pi.setMv(data.getNameMatchValue());

        if (data.getName() != null) {
          pi.setName(data.getName());
        }

        if (data.getLname() != null && !data.getLname().isEmpty()) {
          pi.setLmv(data.getLocalNameMatchValue());
          pi.setLname(data.getLname());
        }

        if (data.getDobType() != null) {
          if (data.getDobType().equalsIgnoreCase(Auth.Pi.Dobt.V.name())) {
            pi.setDobt(Auth.Pi.Dobt.V);
          }
          if (data.getDobType().equalsIgnoreCase(Auth.Pi.Dobt.A.name())) {
            pi.setDobt(Auth.Pi.Dobt.A);
          }
          if (data.getDobType().equalsIgnoreCase(Auth.Pi.Dobt.D.name())) {
            pi.setDobt(Auth.Pi.Dobt.D);
          }
        }

        if (data.getDob() != null) {
          Auth.Dob.Builder dob = Auth.Dob.newBuilder();
          String date = data.getDob();

          if (date.contains("-")) {
            String year = date.substring(0, date.indexOf("-"));
            String month = date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"));
            String day = date.substring(date.lastIndexOf("-") + 1, date.length());

            if (year.length() > 1) {
              dob.setYear(Integer.parseInt(year));
            } else {
              dob.setYear(0);
            }
            if (month.length() > 0) {
              dob.setMonth(Integer.parseInt(month));
            } else {
              dob.setMonth(0);
            }
            if (day.length() > 0) {
              dob.setDay(Integer.parseInt(day));
            } else {
              dob.setDay(0);
            }

          } else {
            dob.setYear(Integer.parseInt(date));
          }
          pi.setDob(dob);

        }

        if (data.getAge() != null) {
          if (StringUtils.isNumeric(data.getAge())) {
            pi.setAge(new Integer(data.getAge()));
          } else {
            if (StringUtils.isNotBlank(data.getAge())) {
              throw new RuntimeException("Age should be numeric");
            }
          }
        }
        if (data.getEmail() != null) {
          pi.setEmail(data.getEmail());
        }

        if (data.getGender() != null) {
          if (data.getGender().equalsIgnoreCase("Male")) {
            pi.setGender(Auth.Pi.Gender.M);
          } else if (data.getGender().equalsIgnoreCase("Female")) {
            pi.setGender(Auth.Pi.Gender.F);
          } else if (data.getGender().equalsIgnoreCase("Transgender")) {
            pi.setGender(Auth.Pi.Gender.T);
          }
        }

        if (data.getPhoneNo() != null) {
          pi.setPhone(data.getPhoneNo());
        }

        demo.setPi(pi);
        isPiPresent = true;
      }

      if (!data.getFullAddress().isEmpty()
          || (data.getLocalFullAddress() != null && !data
          .getLocalFullAddress().isEmpty())) {
        Auth.Pfa.Builder pfa = Auth.Pfa.newBuilder();

        pfa.setMs(Auth.Ms.valueOf(data.getFullAddressMatchStrategy().name()));
        pfa.setMv(data.getFullAddressMatchValue());
        pfa.setAv(data.getFullAddress());

        if (!data.getLocalFullAddress().isEmpty()) {
          pfa.setLav(data.getLocalFullAddress());
          pfa.setLmv(data.getLocalFullAddressMatchValue());
        }

        demo.setPfa(pfa);
        isPfaPresent = true;
      }

      // Add Pa only if one of the constituent attributes have a value
      // specified
      if (data.getCareOf() != null && !data.getCareOf().isEmpty()
          || data.getDistrict() != null && !data.getDistrict().isEmpty()
          || data.getBuilding() != null && !data.getBuilding().isEmpty()
          || data.getLandmark() != null && !data.getLandmark().isEmpty()
          || data.getLocality() != null && !data.getLocality().isEmpty()
          || data.getPinCode() != null && !data.getPinCode().isEmpty()
          || data.getPoName() != null && !data.getPoName().isEmpty()
          || data.getSubdistrict() != null && !data.getSubdistrict().isEmpty()
          || data.getState() != null && !data.getState().isEmpty()
          || data.getStreet() != null && !data.getStreet().isEmpty()
          || data.getVillage() != null && !data.getVillage().isEmpty()) {
        Auth.Pa.Builder pa = Auth.Pa.newBuilder();

        if (data.getAddressMatchStrategy().equals("E")) {
          pa.setMs(Auth.Ms.E);
        }
        if (data.getAddressMatchStrategy().equals("P")) {
          pa.setMs(Auth.Ms.P);
        }
        if (data.getAddressMatchStrategy().equals("F")) {
          pa.setMs(Auth.Ms.F);
        }
        if (data.getCareOf() != null && !data.getCareOf().isEmpty()) {
          pa.setCo(data.getCareOf());
        }

        if (data.getDistrict() != null && !data.getDistrict().isEmpty()) {
          pa.setDist(data.getDistrict());
        }
        if (data.getBuilding() != null && !data.getBuilding().isEmpty()) {
          pa.setHouse(data.getBuilding());
        }
        if (data.getLandmark() != null && !data.getLandmark().isEmpty()) {
          pa.setLm(data.getLandmark());
        }
        if (data.getLocality() != null && !data.getLocality().isEmpty()) {
          pa.setLoc(data.getLocality());
        }
        if (data.getPinCode() != null && !data.getPinCode().isEmpty()) {
          pa.setPc(data.getPinCode());
        }
        if (data.getPoName() != null && !data.getPoName().isEmpty()) {
          pa.setPo(data.getPoName());
        }
        if (data.getSubdistrict() != null && !data.getSubdistrict().isEmpty()) {
          pa.setSubdist(data.getSubdistrict());
        }
        if (data.getState() != null && !data.getState().isEmpty()) {
          pa.setState(data.getState());
        }
        if (data.getStreet() != null && !data.getStreet().isEmpty()) {
          pa.setStreet(data.getStreet());
        }
        if (data.getVillage() != null && !data.getVillage().isEmpty()) {
          pa.setVtc(data.getVillage());
        }
        demo.setPa(pa);

        isPaPresent = true;

      }

      if (isPiPresent || isPaPresent || isPfaPresent) {
        pidBuilder.setDemo(demo);
      }

      Auth.Pv.Builder pv = Auth.Pv.newBuilder();
      if (data.getDynamicPin() != null) {
        pv.setOtp(data.getDynamicPin());
      }
      if (data.getStaticPin() != null) {
        pv.setPin(data.getStaticPin());
      }
      if (pv.hasOtp() || pv.hasPin()) {
        pidBuilder.setPv(pv);
      }

      Calendar calendar = GregorianCalendar.getInstance();
      pidBuilder.setTs(XMLGregorianCalendarImpl.createDateTime(
          calendar.get(Calendar.YEAR),
          calendar.get(Calendar.MONTH) + 1,
          calendar.get(Calendar.DAY_OF_MONTH),
          calendar.get(Calendar.HOUR_OF_DAY),
          calendar.get(Calendar.MINUTE),
          calendar.get(Calendar.SECOND)).toString());
      pidBuilder.setVer("1.0");
    }

    if (data.getBiometrics() != null
        && data.getBiometrics().size() > 0) {

      Auth.Bios.Builder bios = Auth.Bios.newBuilder();

      for (DeviceCollectedAuthData.BiometricData p : data.getBiometrics()) {
        Auth.Bio.Builder bio = Auth.Bio.newBuilder();
        bio.setType(Auth.BioType.valueOf(p.getType().name()));
        bio.setContent(ByteString.copyFrom(p.getBiometricContent()));
        bio.setPosh(Position.valueOf(p.getPosition().name()));

        bios.addBio(bio);
      }

      pidBuilder.setBios(bios);
    }

    System.out.println(pidBuilder.build().toString() + " -- " + pidBuilder.isInitialized());
    //pidBuilderTs = pidBuilder;
    threadLocalPidBuilderTs.set(pidBuilder);
    return pidBuilder.build();

  }
}
