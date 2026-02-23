const express = require("express");
const path = require("path");

const app = express();

// Middleware
app.use(express.json());
app.use(express.static("public"));  // Serve HTML page

// Login API
app.post("/login", (req, res) => {
    const { username, password } = req.body;

    if (username === "admin" && password === "1234") {
        res.json({ message: "Login Successful ✅" });
    } else {
        res.status(401).json({ message: "Invalid Credentials ❌" });
    }
});

// Start Server
app.listen(3000, () => {
    console.log("Server running at http://localhost:3000");
});