package com.aleksandar.devic.meetup.api.get.data;

import com.aleksandar.devic.meetup.api.get.data.domen.Cities;
import com.aleksandar.devic.meetup.api.get.data.domen.City;
import com.aleksandar.devic.meetup.api.get.data.domen.Event;
import com.aleksandar.devic.meetup.api.get.data.domen.Events;
import com.aleksandar.devic.meetup.api.get.data.domen.Group;
import com.aleksandar.devic.meetup.api.get.data.exceptions.MyException;
import com.aleksandar.devic.meetup.api.get.data.util.AccessToken;
import com.aleksandar.devic.meetup.api.get.data.util.Singleton;
import com.aleksandar.devic.meetup.api.get.data.util.Utility;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootGetDataFromMeetupApiApplication implements CommandLineRunner {

    Map<Integer, City> mapCitiesFromRest;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGetDataFromMeetupApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<City> citiesFromRest = null;
        try {
            citiesFromRest = getResponseCitiesFromRest();
            if (citiesFromRest.isEmpty()) {
                System.out.println("Lista Gradova za zemlju : " + Utility.COUNTRY + " je prazna !");
            } else {
                popuniMapCitiesFromRest(citiesFromRest);
                ispisiMapuCitiesFromRest();

                while (true) {
                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        System.out.println("Molimo unesite redni broj grada (ceo broj) za koji zelite da vidite dogadjaje :");
                        String unetiString = br.readLine();

                        int unetiRedniBrojCity = Integer.parseInt(unetiString);
                        City nadjeniCity = pronadjiGradNaOsnovuRednogBroja(unetiRedniBrojCity);

//                        if (Singleton.getInstance().authorizationCode() != null) {
//                            //nastavi rad aplikacije 
//                        } else {
//                            //Obavestenje ne postoji authorizationCode
////                            morate otici u browser i okinuti dogadjaj sa svojim credentialima dole su moji credentiali
                        //npr:  https://secure.meetup.com/oauth2/authorize?client_id=3kbhn0p8dt9g6dcc1cre3euu1s&response_type=code&redirect_uri=http://localhost:8080/restController/authorizationCode
                        //
                        //nisam ovo implmentirao u aplikaciji jer ne moze da se ovaj url pozove iz aplikacije ne znam zasto
                        //vec moze samo iz browsera a glupo je da se pokrene aplikacija 
                        //pa onda da se ulazi na browser da bi se pozvao moj REST da bi dao token
                        // tako da ovaj deo ipak nisam implementirao inace moze da radi i na taj nacin aplikacija
                        //primer REST servisa na koji se dolazi redirectom sa api-ja... 
//                        }
                        List<Event> openEventsForNadjeniGrad = getResponseEventsForNadjeniGrad(nadjeniCity);

                        if (openEventsForNadjeniGrad.isEmpty()) {
                            System.out.println("Ne Postoje Dogadjaji Za Grad :" + nadjeniCity.getCity() + " !!!");
                        } else {
                            //postoje dogadjaji za uneti grad
                            System.out.println("Dogajaji Za Grad : " + nadjeniCity.getCity());
                            System.out.println("-----------------------------");
                            for (Event event : openEventsForNadjeniGrad) {
                                System.out.println(event);
                            }
                            System.out.println("-----------------------------");

                        }

                    } catch (IOException iOException) {
                        throw new Exception(" sa Standardnim Ulazom!!!" + iOException.getMessage());
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("Greska, niste uneli ceo broj za redni broj grada !!!" + numberFormatException.getMessage());
                    } catch (MyException me) {
                        System.out.println(me.getMessage());
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
                    }

                }
            }

        } catch (Exception exception) {
            System.out.println("Greska : " + exception.getMessage());
            exception.printStackTrace();
        }

    }

    private void popuniMapCitiesFromRest(List<City> results) {

        mapCitiesFromRest = new HashMap<>();

        Integer redniBrojGrada = 1;
        for (City city : results) {

//                    Integer redniBrojGrada = Integer.parseInt(city.getZip().substring(6));
            mapCitiesFromRest.put(redniBrojGrada, city);
            redniBrojGrada += 1;

        }
    }

    private void ispisiMapuCitiesFromRest() {
        mapCitiesFromRest.forEach((key, value) -> {
            System.out.println("Redni Broj : " + key + " Grad : " + value.getCity());
        });
    }

    private City pronadjiGradNaOsnovuRednogBroja(int unetiRedniBrojCity) throws Exception {
        City nadjeniCity = mapCitiesFromRest.get(unetiRedniBrojCity);
        if (nadjeniCity != null) {
            return nadjeniCity;
        } else {
            throw new MyException("Grad sa rednim brojem : " + unetiRedniBrojCity + " ne postoji u listi gradova za drzavu : " + Utility.COUNTRY);
        }
    }

    private List<Event> getResponseEventsForNadjeniGrad(City nadjeniCity) throws Exception {
        String urlOpenEventsForNadjeniCity = "https://api.meetup.com/2/open_events?country=" + Utility.COUNTRY + "&city=" + nadjeniCity.getCity() + "&access_token=" + Utility.ACCESS_TOKEN;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Events> responseOpenEventsForNadjeniGrad = restTemplate.exchange(
                urlOpenEventsForNadjeniCity,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Events>() {
        });

        if (responseOpenEventsForNadjeniGrad.getStatusCode() != HttpStatus.OK) {
            throw new Exception("HttpStatus responseOpenEventsForNadjeniGrad : " + responseOpenEventsForNadjeniGrad.getStatusCode() + " " + responseOpenEventsForNadjeniGrad.getStatusCode().name());
        } else {
            return responseOpenEventsForNadjeniGrad.getBody().getResults();

        }
    }

    private List<City> getResponseCitiesFromRest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        final String urlAllCities = "https://api.meetup.com/2/cities?country=" + Utility.COUNTRY;
        ResponseEntity<Cities> responseCitiesFromRest = restTemplate.getForEntity(urlAllCities, Cities.class);

        if (responseCitiesFromRest.getStatusCode() != HttpStatus.OK) {
            throw new Exception("HttpStatus responseCitiesFromRest : " + responseCitiesFromRest.getStatusCode() + " " + responseCitiesFromRest.getStatusCode().name());
        } else {
            //HttpStatus OK 200
            System.out.println("HttpStatus responseCitiesFromRest : " + responseCitiesFromRest.getStatusCode() + " " + responseCitiesFromRest.getStatusCode().name());

            return responseCitiesFromRest.getBody().getResults();

        }

    }

    //                        String urlAllGroupsForNadjeniCity = "https://api.meetup.com/find/groups?zip=" + nadjeniCity.getZip() + "&country=" + Utility.COUNTRY + "&access_token=" + Utility.ACCESS_TOKEN;
////                        String responseGroupsString = restTemplate.getForObject(urlAllGroupsForNadjeniCity, String.class);
////                        System.out.println("responseGroupsString : " + responseGroupsString);
//
////                        ResponseEntity<List<Group>> responseGroupsForNadjeniGrad = restTemplate.exchange(
//                                urlAllGroupsForNadjeniCity,
//                                HttpMethod.GET,
//                                null,
//                                new ParameterizedTypeReference<List<Group>>() {
//                        });
//                        if (responseGroupsForNadjeniGrad.getBody().isEmpty()) {
//                            System.out.println("Lista Grupa za grad : " + nadjeniCity.getCity() + " koji je u zemlji :" + nadjeniCity.getCountry() + " je prazna!!!");
//                        } else {
//                            //lista grupa nije prazna
//                            //System.out.println("responseGroups : " + responseGroupsForNadjeniGrad.getBody().get(0));
//                            System.out.println("Lista Grupa za grad : " + nadjeniCity.getCity() + " koji je u zemlji :" + nadjeniCity.getCountry());
//                            List<Group> groupsForNadjeniGrad = responseGroupsForNadjeniGrad.getBody();
////                            Group prvaGrupa = responseGroups.getBody().get(0);
//                            for (Group group : groupsForNadjeniGrad) {
//                                System.out.println("Grupa : " + group.getName());
//                                //nadji eventove za svaku grupu
//                                String urlAllEventsForCurrentGroup = "https://api.meetup.com/" + group.getUrlname() + "/events?access_token=" + Utility.ACCESS_TOKEN;
////                                System.out.println("urlAllEventsForPrvaGrupa : " + urlAllEventsForCurrentGroup);
//                                ResponseEntity<List<Event>> responseAllEventsForCurrentGroup = restTemplate.exchange(
//                                        urlAllEventsForCurrentGroup,
//                                        HttpMethod.GET,
//                                        null,
//                                        new ParameterizedTypeReference<List<Event>>() {
//                                });
//
//                                if (responseAllEventsForCurrentGroup.getBody().isEmpty()) {
//                                    System.out.println("Lista Dogadjaja za Grupu : " + group.getName() + " je prazna !!!");
//                                } else {
//                                    //lista dogadjaja za currentGroup nije prazna
//                                    List<Event> eventsForCurrentGroup = responseAllEventsForCurrentGroup.getBody();
//                                    for (Event event : eventsForCurrentGroup) {
//                                        System.out.println("Naziv Dogadjaja : " + event.getName());
//                                        System.out.println("Opis dogadjaja : " + event.getDescription());
//                                        System.out.println("");
//                                    }
//                                }
//                            }
//
////                            String urlAllEventsForPrvaGrupa = "https://api.meetup.com/" + prvaGrupa.getUrlname() + "/events?access_token=77d95953b678963c7a7d6d486170a92d";
////                            System.out.println("urlAllEventsForPrvaGrupa : " + urlAllEventsForPrvaGrupa);
//                        }
}
