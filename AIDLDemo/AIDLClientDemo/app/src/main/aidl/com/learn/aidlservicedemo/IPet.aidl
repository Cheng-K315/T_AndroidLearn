// IPet.aidl
package com.learn.aidlservicedemo;

import com.learn.aidlservicedemo.Person;
import com.learn.aidlservicedemo.Pet;

interface IPet {
    List<Pet> getPets(in Person owner);
}