
# 🎬 BookMyShow Clone – Movie Ticket Booking System

## 🚀 Project Overview

This project is a **scalable Movie Ticket Booking System** inspired by BookMyShow.
It is designed using **Spring Boot, Kafka, and Redis** to handle **high concurrency**, prevent **double booking**, and ensure **reliable payment processing** using an **event-driven architecture**.

---

## 🔥 Key Features

### 🎟️ Booking System

* Seat selection and booking per show
* Prevents double booking using **Redis Distributed Lock**
* Seat state management: `AVAILABLE → LOCKED → BOOKED`

### ⚡ Asynchronous Payment Processing

* Kafka-based event-driven payment flow
* Non-blocking booking system
* Simulated payment gateway integration

### 🔁 Idempotent Payment Handling

* Ensures payment is processed only once
* Handles Kafka retries safely

### 📩 Notification System

* Sends notification on:

  * ✅ Payment Success
  * ❌ Payment Failure

### 🧠 High Concurrency Handling

* Redis locking prevents race conditions
* Database state ensures consistency after lock release

---

## 🏗️ System Architecture

```
User Request
    ↓
Booking Service
    ↓ (Kafka Event: booking-created)
Payment Service (Consumer)
    ↓
Kafka Events:
    → payment-success
    → payment-failed
    ↓
Booking Update Service
    ↓
Notification Service
```

## 🚀 Future Enhancements

* ⏳ Seat lock expiry (auto-release after timeout)
* 💳 Real payment gateway integration (Stripe/Razorpay)
* 📊 Monitoring with Prometheus & Grafana
* ☁️ Deployment using Docker & AWS

---
