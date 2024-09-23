from flask import Flask, request, jsonify
import numpy as np
import pickle

# Initialize Flask app
app = Flask(__name__)

# load
with open('model/churn_prediction.pkl', 'rb' , encoding='utf-8') as file:
    model = pickle.load(file)

# prediction
@app.route('/predict', methods=['POST'])
def predict():
    data = request.json

    # Use of a safe way to print the received data
    try:
        print("Received data:", data)
    except Exception as e:
        print(f"Error printing data: {e}")

    # Check for missing keys
    required_keys = [
        'MonthlyCharge', 'AccountWeeks', 'ContractRenewal',
        'DataPlan', 'DataUsage', 'CustServCalls',
        'DayMins', 'DayCalls', 'OverageFee', 'RoamMins'
    ]
    missing_keys = [key for key in required_keys if key not in data]
    if missing_keys:
        return jsonify({'error': f'Missing keys: {", ".join(missing_keys)}'}), 400

    # Convert the data to a numpy array
    try:
        features = np.array([
            float(data['MonthlyCharge']),
            float(data['AccountWeeks']),
            float(data['ContractRenewal']),
            float(data['DataPlan']),
            float(data['DataUsage']),
            float(data['CustServCalls']),
            float(data['DayMins']),
            float(data['DayCalls']),
            float(data['OverageFee']),
            float(data['RoamMins'])
        ]).reshape(1, -1)
        
        print("Features array:", features)  # Debugging line
    except ValueError as e:
        return jsonify({'error': f'Invalid input data: {str(e)}'}), 400

    try:
        # Make prediction
        prediction = model.predict(features)
        print("Raw prediction:", prediction)  # Debugging line
        return jsonify({'prediction': int(prediction[0] > 0.5)})  # Assuming binary classification
    except Exception as e:
        return jsonify({'error': f'Prediction error: {str(e)}'}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)


# -----------------------------------------------------------------------------------------------------

# from flask import Flask, request, jsonify
# import numpy as np
# import pickle
# import logging

# # Initialize Flask app
# app = Flask(__name__)

# # Set up logging
# logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# # Load your model from the pickle file
# with open('model/churn_prediction.pkl', 'rb') as file:
#     model = pickle.load(file)

# def sanitize_input(data):
#     """Sanitize input data to remove non-ASCII characters."""
#     return {key: str(value).encode('ascii', 'ignore').decode('ascii') for key, value in data.items()}

# # Define prediction endpoint
# @app.route('/predict', methods=['POST'])
# def predict():
#     data = request.json

#     # Sanitize the input data
#     data = sanitize_input(data)

#     # Log the received data
#     logging.info("Received data: %s", data)

#     # Check for missing keys
#     required_keys = [
#         'MonthlyCharge', 'AccountWeeks', 'ContractRenewal',
#         'DataPlan', 'DataUsage', 'CustServCalls',
#         'DayMins', 'DayCalls', 'OverageFee', 'RoamMins'
#     ]
#     missing_keys = [key for key in required_keys if key not in data]
#     if missing_keys:
#         return jsonify({'error': f'Missing keys: {", ".join(missing_keys)}'}), 400

#     # Convert the data to a numpy array
#     try:
#         features = np.array([
#             float(data['MonthlyCharge']),
#             float(data['AccountWeeks']),
#             float(data['ContractRenewal']),
#             float(data['DataPlan']),
#             float(data['DataUsage']),
#             float(data['CustServCalls']),
#             float(data['DayMins']),
#             float(data['DayCalls']),
#             float(data['OverageFee']),
#             float(data['RoamMins'])
#         ]).reshape(1, -1)

#         logging.info("Features array: %s", features)  # Log the features array
#     except ValueError as e:
#         return jsonify({'error': f'Invalid input data: {str(e)}'}), 400

#     try:
#         # Make prediction
#         prediction = model.predict(features)
#         logging.info("Raw prediction: %s", prediction)  # Log the raw prediction
#         return jsonify({'prediction': int(prediction[0] > 0.5)})  # Assuming binary classification
#     except Exception as e:
#         return jsonify({'error': f'Prediction error: {str(e)}'}), 500

# if __name__ == '__main__':
#     app.run(host='0.0.0.0', port=5000, debug=True)

