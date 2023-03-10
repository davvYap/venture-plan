"use strict";

let currDate = new Date().toISOString().split("T")[0];
let input = document.getElementById("workdoneDate");
input.value = currDate;
//console.log(currDate);

let editInput = document.getElementById("editWorkdoneDate");
let editButton = document.getElementById("wdUpdateBtn");
editButton.addEventListener("click", function () {
  let oldWorkdoneDateString = document.getElementById("workdoneDate");
  let oldWorkdoneDate = new Date(oldWorkdoneDateString);
  console.log(oldWorkdoneDate);
});
