package com.elementarylogics.imagesliderapp.adaptors.offersAdaptor

import com.elementarylogics.imagesliderapp.dataclases.Product
import java.util.*
import kotlin.collections.ArrayList

class DummyOffersDataUtil {

    companion object {

        fun getEmployeeListSortedByName(): List<Product> {
            val employeeList = getEmployeeList()

            Collections.sort<Product>(
                employeeList
            ) { a1, a2 -> a1.name.compareTo(a2.name) }

            return employeeList
        }


        fun getEmployeeListSortedByRole(): ArrayList<Product> {
            val employeeList = getEmployeeList()

            Collections.sort<Product>(
                employeeList
            ) { a1, a2 -> a2.id.compareTo(a1.id) }
            return employeeList
        }

        private fun getEmployeeList(): ArrayList<Product> {
            val employees = ArrayList<Product>()

            employees.add(
                Product(
                    1,
                    "oil buy 1 get 1",
                    "Rs 200",
                    "Rs 150",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    2,
                    "ghee origonal buy to get 1 fre",
                    "Rs 200",
                    "180",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    3,
                    "buy 1 get 1",
                    "Rs 300",
                    "Rs 150",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    4,
                    "With 2 bottels get 1 bottle free",
                    "Rs 300",
                    "Rs 200",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver"
                    ,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    5,
                    "2 in one pack",
                    "Rs 150",
                    "Rs 100",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    6,
                    "3 in one pack",
                    "Rs 100",
                    "Rs 75",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    7,
                    "Buy 1 pack get 2 pack",
                    "Rs 200",
                    "Rs 50",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    8,
                    "Buy 1 get 1",
                    "200",
                    "100",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            employees.add(
                Product(
                    9,
                    "Buy 2 litters get 1 littre free",
                    "Rs 300",
                    "Rs 150",
                    "Unit",
                    "7 % Off",
                    "Same Day Deliver",
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    0,
                    "https://www.google.com/search?q=fashion+images+hd&sxsrf=ACYBGNQo2rbBPEd9HtP5MULeGDLxzW0Nrw:1576824213761&tbm=isch&source=iu&ictx=1&fir=vKDYp_NKJ3axOM%253A%252CnQ3LcCwXurLB2M%252C_&vet=1&usg=AI4_-kS4J_V6bUjlnMa4JgXy55GhbLJk9A&sa=X&ved=2ahUKEwidlon5z8PmAhUPrxoKHUKhDYEQ9QEwB3oECAoQPA#imgrc=Mvz_ACXtcDOZIM:&vet=1",
                    "On Same Day Delivery"
                )
            )
            return employees
        }


    }
}