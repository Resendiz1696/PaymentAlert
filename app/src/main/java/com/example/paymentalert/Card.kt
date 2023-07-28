package com.example.paymentalert

data class Card(var banco : String ?= null,
                var fecha_corte : String ?= null,
                var fecha_pago : String ?= null,
                var nombre : String ?= null,
                var numero : String ?= null)
