from django.http import JsonResponse, HttpResponseForbidden, HttpResponseNotFound
from django.contrib.auth.models import User
import json

def update(request):
    if request.user.is_authenticated:

        userID = json.loads(request.body)['userID']
        email = json.loads(request.body)['email']
        isAdmin = json.loads(request.body)['isAdmin']

        try:
            user = User.objects.get(id=userID)
            user.email = email
            user.is_superuser = isAdmin
            user.save()
            return JsonResponse({})

        except User.DoesNotExist:
            return HttpResponseNotFound("No matching user found")

    else:
        return HttpResponseForbidden("Not authorized")
