package com.example.ratingsoft.ui




import com.google.firebase.firestore.FirebaseFirestore

private var _binding: FragmentClasificacionBinding? = null
private val binding get() = _binding!!
private var setupExecuted = false
private val db = FirebaseFirestore.getInstance()

