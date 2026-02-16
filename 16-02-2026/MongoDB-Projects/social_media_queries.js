db = db.getSiblingDB("socialMediaDB");

// ============================================
// Social Media - Advanced Queries
// ============================================

// 1️⃣ Users with Most Followers
db.Users.aggregate([
  {
    $project: {
      name: 1,
      totalFollowers: { $size: "$followers" }
    }
  },
  { $sort: { totalFollowers: -1 } }
])

// 2️⃣ Posts with Highest Likes & Comments
db.Posts.aggregate([
  {
    $project: {
      content: 1,
      totalLikes: { $size: "$likes" },
      totalComments: { $size: "$comments" }
    }
  },
  { $sort: { totalLikes: -1, totalComments: -1 } }
])

// 3️⃣ Aggregate User Activity Over Time (Monthly Posts)
db.Posts.aggregate([
  {
    $group: {
      _id: {
        year: { $year: "$createdAt" },
        month: { $month: "$createdAt" }
      },
      totalPosts: { $sum: 1 }
    }
  },
  { $sort: { "_id.year": 1, "_id.month": 1 } }
])

// 4️⃣ Most Active Users (Post Count)
db.Posts.aggregate([
  {
    $group: {
      _id: "$userId",
      totalPosts: { $sum: 1 }
    }
  },
  { $sort: { totalPosts: -1 } }
])
