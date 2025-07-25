#!/bin/bash

echo "🚀 Testing Email Event Architecture"
echo "=================================="

# Check if RabbitMQ is running
echo "📋 Checking RabbitMQ status..."
if ! curl -s http://localhost:15672 > /dev/null; then
    echo "❌ RabbitMQ is not running. Please start it first:"
    echo "   cd backend && docker-compose up -d"
    exit 1
fi
echo "✅ RabbitMQ is running"

# Check if the application is running
echo "📋 Checking application status..."
if ! curl -s http://localhost:8080/actuator/health > /dev/null; then
    echo "❌ Application is not running. Please start it first."
    exit 1
fi
echo "✅ Application is running"

echo ""
echo "🧪 Testing email endpoints..."

# Test welcome email
echo "📧 Testing welcome email..."
WELCOME_RESPONSE=$(curl -s -X POST http://localhost:8080/api/email/send-welcome \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","userName":"Test User"}')

if [[ $WELCOME_RESPONSE == *"successfully"* ]]; then
    echo "✅ Welcome email test passed"
else
    echo "❌ Welcome email test failed: $WELCOME_RESPONSE"
fi

# Test sign-in code email
echo "📧 Testing sign-in code email..."
SIGNIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/email/send-signin-code \
  -H "Content-Type: application/json" \
  -d '{"email":"signin@example.com","code":"123456","expirationInMinutes":10}')

if [[ $SIGNIN_RESPONSE == *"successfully"* ]]; then
    echo "✅ Sign-in code email test passed"
else
    echo "❌ Sign-in code email test failed: $SIGNIN_RESPONSE"
fi

# Test generic email
echo "📧 Testing generic email..."
GENERIC_RESPONSE=$(curl -s -X POST http://localhost:8080/api/email/send \
  -H "Content-Type: application/json" \
  -d '{"to":"generic@example.com","subject":"Test Subject","templateName":"test-template","templateData":{"key":"value"}}')

if [[ $GENERIC_RESPONSE == *"successfully"* ]]; then
    echo "✅ Generic email test passed"
else
    echo "❌ Generic email test failed: $GENERIC_RESPONSE"
fi

echo ""
echo "🔍 Check RabbitMQ Management UI: http://localhost:15672"
echo "   Username: guest"
echo "   Password: guest"
echo "   Look for queue: email.send.queue"
echo ""
echo "📊 Check application logs for event publishing and processing"
echo "🎯 Architecture test completed!" 