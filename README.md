# 🌿 Crop Disease Classification using Deep Learning & Explainable AI

<p align="center">
  <img src="https://img.shields.io/badge/Python-3.10-blue?style=for-the-badge&logo=python">
  <img src="https://img.shields.io/badge/PyTorch-Deep%20Learning-red?style=for-the-badge&logo=pytorch">
  <img src="https://img.shields.io/badge/OpenCV-Computer%20Vision-green?style=for-the-badge&logo=opencv">
  <img src="https://img.shields.io/badge/Grad--CAM-Explainable%20AI-orange?style=for-the-badge">
  <img src="https://img.shields.io/badge/License-MIT-success?style=for-the-badge">
</p>

---

## 🚀 Overview

Crop diseases significantly reduce agricultural productivity worldwide. Early detection can save crops, increase yield, and reduce unnecessary pesticide usage.

This project leverages **Transfer Learning** with pretrained Convolutional Neural Networks to accurately classify plant diseases from leaf images. To improve transparency and trust, predictions are explained using **Grad-CAM (Gradient-weighted Class Activation Mapping)**, allowing users to visualize which regions of the leaf influenced the model's decision.

---

# ✨ Features

✅ Plant Disease Classification

✅ Transfer Learning (ResNet18 / EfficientNet)

✅ 38 Disease Categories

✅ PlantVillage Dataset

✅ Grad-CAM Explainability

✅ Training & Evaluation Pipeline

✅ Confusion Matrix

✅ Precision, Recall & F1 Score

✅ Easy Prediction on Custom Images

---

# 🧠 Model Architecture

```
Input Leaf Image
        │
        ▼
Image Preprocessing
        │
        ▼
Pretrained CNN
(ResNet18 / EfficientNet-B0)
        │
        ▼
Feature Extraction
        │
        ▼
Fully Connected Layer
        │
        ▼
Disease Prediction
        │
        ▼
Grad-CAM Heatmap
```

---

# 📂 Project Structure

```
Crop-Disease-Classification
│
├── configs/
├── data/
├── reports_archive/
├── src/
│   ├── data/
│   ├── explain/
│   ├── models/
│   ├── train.py
│   ├── eval.py
│   ├── metrics.py
│   └── visualize.py
│
├── requirements.txt
├── LICENSE
└── README.md
```

---

# 🍃 Dataset

**Dataset:** PlantVillage

- 📸 54,000+ Images
- 🌱 38 Classes
- 🍅 Tomato
- 🍎 Apple
- 🌽 Corn
- 🍇 Grape
- 🥔 Potato
- 🌶 Pepper
- 🟢 Healthy Leaves

---

# ⚙️ Technologies Used

| Technology | Purpose |
|------------|----------|
| Python | Programming Language |
| PyTorch | Deep Learning |
| TorchVision | Pretrained Models |
| OpenCV | Image Processing |
| NumPy | Numerical Computation |
| Matplotlib | Visualization |
| Grad-CAM | Explainable AI |

---

# 📊 Workflow

```
Dataset
   │
   ▼
Image Augmentation
   │
   ▼
Transfer Learning
   │
   ▼
Training
   │
   ▼
Evaluation
   │
   ▼
Prediction
   │
   ▼
Grad-CAM Visualization
```

---

# 📈 Evaluation Metrics

- Accuracy
- Precision
- Recall
- F1 Score
- Confusion Matrix

---

# 🔥 Explainable AI

Instead of acting as a "black box", the model provides visual explanations using **Grad-CAM**.

This allows users to understand:

- Which infected regions were detected
- Why a prediction was made
- Model confidence

---

# 💻 Installation

```bash
git clone https://github.com/laksh0777/Crop-Disease-Classification.git

cd Crop-Disease-Classification

pip install -r requirements.txt
```

---

# ▶️ Training

```bash
python src/train.py
```

---

# 🔍 Evaluation

```bash
python src/eval.py
```

---

# 🎯 Prediction

```bash
python predict.py --image sample.jpg
```

---

# 🌍 Applications

🌾 Smart Agriculture

🍅 Disease Monitoring

🚜 Precision Farming

📈 Crop Yield Improvement

🛰 Agricultural AI Research

---

# 📌 Future Enhancements

- Mobile Application
- Real-Time Disease Detection
- Drone-Based Crop Monitoring
- Multi-language Support
- Cloud Deployment
- Web Dashboard

---

# 🤝 Contributing

Contributions are welcome!

Feel free to fork this repository and submit pull requests.

---

# 📜 License

This project is licensed under the MIT License.

---

# 👨‍💻 Author

**Laksh S Dandare**

B.Tech Computer Science Engineering

SRM Institute of Science and Technology

GitHub: https://github.com/laksh0777

---

## ⭐ If you found this project useful, don't forget to Star the repository!
