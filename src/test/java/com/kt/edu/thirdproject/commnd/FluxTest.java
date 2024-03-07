package com.kt.edu.thirdproject.commnd;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FluxTest {

    @Test
    public void flux_just_consumer() {
        List<String> names = new ArrayList<String>();
        Flux<String> flux = Flux.just("자바","스칼라","파이썬").log();
        flux.subscribe(s -> {
            System.out.println("시퀀스 수신 : " + s);
            names.add(s);
        });
        assertEquals(names, Arrays.asList("자바","스칼라","파이썬"));
    }

    @Test
    public void ArrayTest(){
        List<String> names = new ArrayList<String>();
//        String[] names = { "자바","스칼라","파이썬" };
        names.add("자바");
        names.add("스칼라");
        names.add("파이썬");
        for (String s: names) {
            System.out.println("시퀀스 수신 : " + s);
        }

        Iterator iter = names.iterator();
        while (iter.hasNext()){
            System.out.println("시퀀스 수신 : " + iter.next());
        }
    }

    @Test
    public void ArraySteamTest() {
        List<String> names = new ArrayList<String>();
        names.add("자바");
        names.add("스칼라");
        names.add("파이썬");

        List<String> FilteredList = names.stream()
                .filter(s -> s.equals("자바"))
//                .forEach(s -> {
//                    System.out.println("시퀀스 수신 : " + s)
//                })
                .collect(Collectors.toList());
        System.out.println("시퀀스 수신 : " + FilteredList);

//        Flux<String> flux = Flux.fromArray(names).log();
//        flux.subscribe(names::add);
//        assertEquals(names, Arrays.asList("자바", "스칼라", "파이썬"));

    }

    @Test
    public void ArraySteamTest2() {
        List<Member> names = new ArrayList<>();

        names.add(new Member("자바", 20));
        names.add(new Member("스칼라", 30));
        names.add(new Member("파이썬", 50));

        List<Member> FilteredList = names.stream()
                .filter(s -> s.getAge() > 30)
                .collect(Collectors.toList());
        System.out.println("시퀀스 수신 : " + FilteredList);

        List<Integer> ageList = names.stream()
                .map(s -> s.getAge())
                .collect(Collectors.toList());
        System.out.println("ageList : " + ageList);

        List<String> nameList = names.stream()
                .map(Member::getName)
                .collect(Collectors.toList());
        System.out.println("nameList : " + nameList);

    }

    @Test
    public void SubscribeTest() {
        Flux.range(1,3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("[Subscriber] onSubscriber");
                        subscription.request(Long.MAX_VALUE); // 이걸 넣어줘야 진행하게 됨. 숫자 모르면 Long.MAX_VALUE
                    }


                    @Override
                    public void onNext(Integer item) {
                        System.out.println("[Subscriber] onNext : " + item);

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("[Subscriber] onError : " + throwable.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("[Subscriber] Complete");

                    }
                });
    }

    @Test
    public void FluxDefaultTest(){
        Flux.empty().defaultIfEmpty(10).subscribe(System.out::println); //값이 없으면 저 defaultV 저부분을 넣어라
    }

    @Test
    public void flatMapMany(){
        Mono.just(1)
                .flatMapMany(item -> Flux.just(3, 2, 1))
                .subscribe(
                        item -> System.out.println("[Subscribe] onNext : " + item),
                        e -> System.out.println("[Subscribe] onErr : " + e.getMessage()),
                        () -> System.out.println("[Subscribe] onComplit")
                );
    }

    @Test
    public void zip(){
//        //flux1 = ~~ 어쩌고 해줘야 함
//        Flux.zip(flux1, flux2, flux3)
//                .subscribe(
//                        item -> System.out.println("[Subscribe] onNext : " + item),
//                        e -> System.out.println("[Subscribe] onErr : " + e.getMessage()),
//                        () -> System.out.println("[Subscribe] onComplit")
//                );
    }

}
