// ============================================
// Social Media Platform - Schema & Sample Data
// ============================================

db = db.getSiblingDB("socialMediaDB");

// Drop existing collections (for clean setup)
db.Users.drop()
db.Posts.drop()

// ----------------------------
// Users Collection
// ----------------------------
db.Users.insertMany([
  {
    name: "Prabhat",
    email: "prabhat@gmail.com",
    bio: "Full Stack Developer",
    followers: [],
    following: [],
    createdAt: new Date()
  },
  {
    name: "Rahul",
    email: "rahul@gmail.com",
    bio: "Backend Engineer",
    followers: [],
    following: [],
    createdAt: new Date()
  },
  {
    name: "Amit",
    email: "amit@gmail.com",
    bio: "Frontend Developer",
    followers: [],
    following: [],
    createdAt: new Date()
  }
])

// Add follower relationships
let user1 = db.Users.findOne({ name: "Prabhat" })
let user2 = db.Users.findOne({ name: "Rahul" })
let user3 = db.Users.findOne({ name: "Amit" })

db.Users.updateOne(
  { _id: user1._id },
  {
    $push: {
      followers: { userId: user2._id, followedAt: new Date() }
    }
  }
)

db.Users.updateOne(
  { _id: user1._id },
  {
    $push: {
      followers: { userId: user3._id, followedAt: new Date() }
    }
  }
)

// ----------------------------
// Posts Collection (Embedded Likes & Comments)
// ----------------------------
db.Posts.insertMany([
  {
    userId: user1._id,
    content: "Learning MongoDB 🚀",
    createdAt: new Date(),
    likes: [
      { userId: user2._id, likedAt: new Date() },
      { userId: user3._id, likedAt: new Date() }
    ],
    comments: [
      { userId: user2._id, text: "Nice post!", createdAt: new Date() }
    ]
  },
  {
    userId: user2._id,
    content: "Aggregation Framework is powerful 🔥",
    createdAt: new Date(),
    likes: [{ userId: user1._id, likedAt: new Date() }],
    comments: []
  }
])
