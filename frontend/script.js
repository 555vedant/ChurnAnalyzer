alert("Try to make a float value in form of ...X.YZ format for GREAT accuracy ")
document.getElementById("churnForm").addEventListener("submit", async function (e) {
    e.preventDefault(); 

    // Collect form data into an object
    const formData = new FormData(this);
    const data = Object.fromEntries(formData);

    // Parse all form data values to appropriate types
    const parsedData = {
        MonthlyCharge: parseFloat(data.MonthlyCharge),
        AccountWeeks: parseInt(data.AccountWeeks),
        ContractRenewal: parseInt(data.ContractRenewal),
        DataPlan: parseInt(data.DataPlan),
        DataUsage: parseFloat(data.DataUsage),
        CustServCalls: parseInt(data.CustServCalls),
        DayMins: parseFloat(data.DayMins),
        DayCalls: parseInt(data.DayCalls),
        OverageFee: parseFloat(data.OverageFee),
        RoamMins: parseFloat(data.RoamMins),
    };

    try {
        const response = await fetch("http://localhost:8080/api/predict", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(parsedData), 
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const result = await response.json();
        
        document.getElementById("result").innerText =
            result.prediction === 1 ? "Churn" : "No Churn";

    } catch (error) {
        console.error("Error:", error);
        document.getElementById("result").innerText =
            "Error occurred while predicting: " + error.message;
    }
});
