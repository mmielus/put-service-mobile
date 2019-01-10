package com.example.michal.myapplication;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card
{
   Long id;
   String description;
   List<String> dimensions;
   String phoneNumber;
   String email;
   String city;
   String street;
   String houseNumber;
   String payment;
}