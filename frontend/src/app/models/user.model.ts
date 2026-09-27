export interface AuthResponse {
  token: string;
  userId: number;
  fullName: string;
  email: string;
  roles: string[];
}

export interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}
