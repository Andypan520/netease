/*
 * Copyright (C) 2015 John Leacox
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.leacox.motif.MatchesExact;
import  com.leacox.motif.cases.ListConsCases;

import common.OptionalUtil;

import java.util.Arrays;
import java.util.List;
import  java.util.Optional;

import static com.leacox.motif.MatchesAny.any;
import static com.leacox.motif.Motif.match;
import static com.leacox.motif.cases.PrimitiveCases.caseLong;
import static java.util.Arrays.*;
import static org.openjdk.jmh.util.Optional.none;

/**
 * An example of using pattern matching for implementing factorial.
 *
 * @author John Leacox
 */
public class FactorialExample {
  /**
   * A traditional implementation of factorial with a conditional.
   */
  public static long factConditional(long n) {
    if (n == 0) {
      return 1;
    } else {
      return n * factConditional(n - 1);
    }
  }

  /**
   * An implementation of factorial using motif pattern matching.
   */
  public static long factMatching(long n) {
    return match(n)
        .when(caseLong(0)).get(() -> 1L)
        .when(caseLong(any())).get(i -> i * factMatching(i - 1))
        .getMatch();
  }

//  public static void personMatch(){
//    Optional<Person> personOpt = OptionalUtil.get(() -> new Person("haha"));
//    match(personOpt)
//            .when(any()).then(person -> System.out.println(person))
//            .when(none()).then(() -> System.out.println("Person not found"))
//            .doMatch();
//  }

  public static void listConsMatching(){
    List<String> list = asList("a", "b", "c", "d");
    match(list)
            .when(ListConsCases.nil()).then(() -> System.out.println("Empty List"))
            .when(ListConsCases.headNil(MatchesExact.eq("b"))).then(() -> System.out.println("Singleton List of 'b'"))
            .when(ListConsCases.headNil(any())).then(head -> System.out.println("Singleton List of " + head))
            .when(ListConsCases.headTail(any(), any())).then(
            (head, tail) -> System.out.println("head: " + head + " Remaining: " + tail))
            .doMatch();
  }


  public static void main(String[] args){
    long result = FactorialExample.factMatching(4);
//    System.out.println(result);
    //personMatch();
    listConsMatching();
  }
}
