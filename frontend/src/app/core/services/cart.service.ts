import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { CartItem, CartItemRequest } from '../../models/cart.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly apiUrl = `${environment.apiBaseUrl}/cart`;

  private items = signal<CartItem[]>([]);

  readonly cartItems = computed(() => this.items());
  readonly itemCount = computed(() => this.items().reduce((sum, i) => sum + i.quantity, 0));
  readonly cartTotal = computed(() =>
    this.items().reduce((sum, i) => sum + i.product.price * i.quantity, 0)
  );

  constructor(private http: HttpClient) {}

  refresh(): void {
    this.http.get<CartItem[]>(this.apiUrl).subscribe({
      next: (items) => this.items.set(items),
      error: () => this.items.set([]),
    });
  }

  addOrUpdate(request: CartItemRequest): Observable<CartItem> {
    return this.http.post<CartItem>(`${this.apiUrl}/items`, request).pipe(
      tap(() => this.refresh())
    );
  }

  remove(cartItemId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/items/${cartItemId}`).pipe(
      tap(() => this.refresh())
    );
  }

  clear(): Observable<void> {
    return this.http.delete<void>(this.apiUrl).pipe(
      tap(() => this.items.set([]))
    );
  }
}
