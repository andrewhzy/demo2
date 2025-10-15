#!/usr/bin/env python3
"""
API Test Script for Blocked Users Service
Translates HTTP requests from api-test.http into Python
"""

import requests
import json

# Base URL configuration
BASE_URL = "http://localhost:8080/rest/api/v1"

def block_single_user():
    """Block a single user"""
    url = f"{BASE_URL}/blocked-users"
    payload = [
        {
            "blockedUserId": "user124",
            "blockedBy": "admin456",
            "blockReason": "Violation of terms of service"
        }
    ]

    response = requests.post(url, json=payload, headers={"Content-Type": "application/json"})
    print(f"Block Single User - Status: {response.status_code}")
    print(f"Response: {response.text}\n")
    return response

def block_multiple_users():
    """Block multiple users"""
    url = f"{BASE_URL}/blocked-users"
    payload = [
        {
            "blockedUserId": "user789",
            "blockedBy": "moderator001",
            "blockReason": "Spam activity detected"
        },
        {
            "blockedUserId": "user456",
            "blockedBy": "admin789",
            "blockReason": "Harassment"
        },
        {
            "blockedUserId": "user321",
            "blockedBy": "moderator001",
            "blockReason": "Multiple violations"
        }
    ]

    response = requests.post(url, json=payload, headers={"Content-Type": "application/json"})
    print(f"Block Multiple Users - Status: {response.status_code}")
    print(f"Response: {response.text}\n")
    return response

def get_all_blocked_users():
    """Get all blocked users"""
    url = f"{BASE_URL}/blocked-users"

    response = requests.get(url, headers={"Content-Type": "application/json"})
    print(f"Get All Blocked Users - Status: {response.status_code}")
    print(f"Response: {response.text}\n")
    return response

def get_blocked_users_by_ids():
    """Get blocked users by IDs"""
    url = f"{BASE_URL}/blocked-users"
    payload = ["user789", "user456", "user321"]

    response = requests.get(url, json=payload, headers={"Content-Type": "application/json"})
    print(f"Get Blocked Users by IDs - Status: {response.status_code}")
    print(f"Response: {response.text}\n")
    return response

def unblock_single_user():
    """Unblock a single user"""
    url = f"{BASE_URL}/blocked-users/unblock"
    payload = ["user124"]

    response = requests.post(url, json=payload, headers={"Content-Type": "application/json"})
    print(f"Unblock Single User - Status: {response.status_code}")
    print(f"Response: {response.text}\n")
    return response

def unblock_multiple_users():
    """Unblock multiple users"""
    url = f"{BASE_URL}/blocked-users/unblock"
    payload = ["user789", "user456", "user321"]

    response = requests.post(url, json=payload, headers={"Content-Type": "application/json"})
    print(f"Unblock Multiple Users - Status: {response.status_code}")
    print(f"Response: {response.text}\n")
    return response

def run_all_tests():
    """Run all API tests"""
    print("=" * 60)
    print("Running Blocked Users API Tests")
    print("=" * 60 + "\n")

    try:
        # Test 1: Block single user
        print("1. Block Single User")
        print("-" * 60)
        block_single_user()

        # Test 2: Block multiple users
        print("2. Block Multiple Users")
        print("-" * 60)
        block_multiple_users()

        # Test 3: Get all blocked users
        print("3. Get All Blocked Users")
        print("-" * 60)
        get_all_blocked_users()

        # Test 4: Get blocked users by IDs
        print("4. Get Blocked Users by IDs")
        print("-" * 60)
        get_blocked_users_by_ids()

        # Test 5: Unblock single user
        print("5. Unblock Single User")
        print("-" * 60)
        unblock_single_user()

        # Test 6: Unblock multiple users
        print("6. Unblock Multiple Users")
        print("-" * 60)
        unblock_multiple_users()

        print("=" * 60)
        print("All tests completed")
        print("=" * 60)

    except requests.exceptions.ConnectionError:
        print("ERROR: Could not connect to the server. Is it running on localhost:8080?")
    except Exception as e:
        print(f"ERROR: {str(e)}")

if __name__ == "__main__":
    run_all_tests()
